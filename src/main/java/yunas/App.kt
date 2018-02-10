package yunas

import org.eclipse.jetty.server.Server
import org.slf4j.LoggerFactory
import yunas.configuration.Configuration
import yunas.configuration.ConfigurationFactory
import yunas.configuration.DefaultConfigurationFactory
import yunas.db.Databases
import yunas.db.strategy.DefaultDataSourceStrategy
import yunas.exception.YunasExceptionProvider
import yunas.http.DefaultContentType
import yunas.http.HttpMethod
import yunas.router.Router
import yunas.server.EmbeddedServerFactory
import yunas.server.JettyFactory
import yunas.template.TemplateEngine
import yunas.template.ThymeleafEngine

/**
 * App Instance.
 *
 * Singleton Instance.
 *
 * @author  Yosuke Kobayashi
 */
internal class App// Hide Constructor
private constructor(isWeb: Boolean) {

    lateinit var configuration: Configuration

    lateinit var server: Server

    var router = Router()

    var before: Interceptor? = null

    var after: Interceptor? = null

    var errorController: ErrorController? = null

    // Default Thymeleaf
    var templateEngine: TemplateEngine = ThymeleafEngine()

    private var isInit = false

    init {

        perform(DefaultConfigurationFactory(), JettyFactory(), isWeb)
    }


    @Synchronized
    fun kill() {

        try {
            this.server!!.stop()
        } catch (th: Throwable) {
            // Fail to Stop
            LOG.error(th.message)
            th.printStackTrace()
        }

    }


    private fun perform(confFactory: ConfigurationFactory, serverFactory: EmbeddedServerFactory, isWeb: Boolean) {

        if (isInit) {
            return
        }

        // Build Configuration, Server
        val conf = confFactory.create()
        this.configuration = conf
        this.server = serverFactory.create(conf)

        // Create Databases ConnectionPool
        Databases.init(conf, DefaultDataSourceStrategy())

        if (!isWeb) {
            isInit = true
            return
        }

        Thread {

            try {

                this@App.server!!.start()
                this@App.server!!.join()

            } catch (e: Exception) {
                e.printStackTrace()
                LOG.error("Halt server: " + e.message)
                Thread.currentThread().interrupt()
            }
        }.start()

        isInit = true

    }

    fun add(method: HttpMethod, path: String, controller: Controller, defaultContentType: DefaultContentType) {
        router.add(method, path, controller, defaultContentType)
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(App::class.java)

        @Synchronized
        fun init(): App {

            try {
                return App(true)
            } catch (th: Throwable) {
                // Fail to init
                LOG.error(th.message)
                th.printStackTrace()
                throw YunasExceptionProvider().failToInit(th)
            }

        }

        @Synchronized
        fun initBatch(): App {

            try {
                return App(false)
            } catch (th: Throwable) {
                // Fail to init
                LOG.error(th.message)
                th.printStackTrace()
                throw YunasExceptionProvider().failToInit(th)
            }

        }
    }
}



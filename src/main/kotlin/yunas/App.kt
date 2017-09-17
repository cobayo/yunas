package yunas

import org.eclipse.jetty.server.Server
import org.slf4j.LoggerFactory
import yunas.configuration.Configuration
import yunas.configuration.ConfigurationFactory
import yunas.configuration.DefaultConfigurationFactory
import yunas.db.Databases
import yunas.db.DefaultDataSourceStrategy
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
internal class App

// Hide Constructor
private constructor() {

    var configuration: Configuration? = null
        private set

    private var server: Server? = null

    val router = Router()

    var before: Interceptor? = null

    var after: Interceptor? = null

    var errorController: ErrorController? = null

    // Default Thymeleaf
    var templateEngine: TemplateEngine = ThymeleafEngine()

    private var isInit = false

    var isJar = false
        get

    init {
        perform(DefaultConfigurationFactory(), JettyFactory())
    }

    private fun perform(confFactory: ConfigurationFactory, serverFactory: EmbeddedServerFactory) {

        if (isInit) {
            return
        }

        checkResourceBase()

        Thread {

            try {

                // Build Configuration, Server
                val conf  = confFactory.create()
                this.configuration = conf
                this.server = serverFactory.create(conf)

                // Create Databases ConnectionPool
                Databases.init(conf, DefaultDataSourceStrategy())
                this.server!!.start()
                this.server!!.join()

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
                return App()
            } catch (th: Throwable) {
                // Fail to init
                LOG.error(th.message)
                th.printStackTrace()
                throw YunasExceptionProvider().failToInit(th.cause)
            }

        }
    }


    /**
     *  Dir for Static Files.
     *
     *  jar -> /static/...
     *  local -> rootDir/src/main/resources/static
     */
    private fun checkResourceBase() {

            val rootDir = this.javaClass.getResource(this.javaClass.simpleName + ".class").path

            if (rootDir.contains("jar",true) && rootDir.contains("file", false)) {
                isJar = true
            }

        }
}

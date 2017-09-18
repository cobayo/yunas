package yunas.server

import org.eclipse.jetty.server.Connector
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.slf4j.LoggerFactory
import yunas.configuration.Configuration
import yunas.filter.StaticFilter
import yunas.handler.AppServlet
import yunas.handler.AppServletHandler
import yunas.handler.BaseErrorHandler
import java.io.File
import java.util.*
import java.util.jar.JarFile
import javax.servlet.DispatcherType

/**
 * Create Embedded Jetty Server for Yunas.
 *
 * @author Yosuke Kobayashi
 */
class JettyFactory : EmbeddedServerFactory {

    override fun create(conf: Configuration): Server {

        // Create ServletHandler.
        val contextHandler = AppServletHandler(ServletContextHandler.SESSIONS or ServletContextHandler.SECURITY)

        // Set Context Path. Usually "/"
        contextHandler.contextPath = "/"
        if (conf.maxFormContentSize == 0) {
            // Default
            contextHandler.maxFormContentSize = conf.maxFormContentSize
        }

        contextHandler.errorHandler = BaseErrorHandler()
        contextHandler.resourceBase = resourceBase
        contextHandler.addFilter(StaticFilter::class.java, "/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD))
        // Create servlet for Static Resources.
        val defaultServletHolder = ServletHolder(DefaultServlet())
        defaultServletHolder.setInitParameter("dirAllowed", "true")
        defaultServletHolder.setInitParameter("baseResource", contextHandler.resourceBase )
        defaultServletHolder.setInitParameter("pathInfoOnly", "true")

        LOG.info("ResourceBase is " + contextHandler.resourceBase )

        // Add static files Servlet to Handler.
        contextHandler.addServlet(defaultServletHolder, staticServletPath + "/*")

        // Add App Servlet to Handler.
        contextHandler.addServlet(AppServlet::class.java, "/*")

        // Create Jetty Server
        val maxThreads = conf.maxThreads
        val minThreads = conf.minThreads
        val threadPool = QueuedThreadPool(maxThreads, minThreads)
        val server = Server(threadPool)

        // Set Http Port
        val connector = ServerConnector(server)
        connector.port = conf.port
        server.connectors = arrayOf<Connector>(connector)

        // Add Handlers to Server
        server.handler = contextHandler

        return server
    }

    /**
     * Returns StaticFile Root Dir.
     *
     *  At First, Search /static in Jar File(fat jar)
     *
     *  At Second, Search current Dir + src/main/resources/static
     */
    private val resourceBase: String
        get() {

            try {

                val jarPath = this.javaClass.protectionDomain.codeSource.location.path
                val jarFile = JarFile(jarPath)
                val entry = jarFile.getJarEntry(STATIC_FILES_DIR + File.separator)

                if (entry != null) {

                    val classPath = this.javaClass.getResource("JettyFactory.class")
                    val path = "/yunas/server".replace("/",File.separator)

                    return classPath.toURI().toASCIIString().replaceFirst(path + File.separator + "JettyFactory.class", File.separator + STATIC_FILES_DIR)
                }

            } catch (e : Exception) {
                LOG.warn(e.message)
            }

            return System.getProperty("user.dir") +
                    File.separator +
                    "src" +
                    File.separator +
                    "main" +
                    File.separator +
                    "resources" +
                    File.separator +
                    STATIC_FILES_DIR +
                    File.separator

        }

    companion object {

        private val LOG = LoggerFactory.getLogger(JettyFactory::class.java)

        // Static files(*.css, *.js etc.. ) in src/main/resources/static
        private val STATIC_FILES_DIR = "static"

        val staticServletPath = "/yunas_static_servlet"
    }
}

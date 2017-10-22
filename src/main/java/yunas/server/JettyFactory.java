package yunas.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.configuration.Configuration;
import yunas.filter.StaticFilter;
import yunas.handler.AppServlet;
import yunas.handler.AppServletHandler;
import yunas.handler.BaseErrorHandler;

import javax.servlet.DispatcherType;
import java.io.File;
import java.net.URL;
import java.util.EnumSet;

/**
 * Create Embedded Jetty Server for Yunas.
 *
 * @author Yosuke Kobayashi
 */
public class JettyFactory implements EmbeddedServerFactory {

    private Logger LOG = LoggerFactory.getLogger(JettyFactory.class);

    // Static files(*.css, *.js etc.. ) in src/main/resources/static
    private static final String STATIC_FILES_DIR = "static";

    private static final String STATIC_SERVLET_PATH = "/yunas_static_servlet";

    public static String getStaticServletPath() {
        return STATIC_SERVLET_PATH;
    }

    @Override
    public Server create(Configuration conf) {

        // Create ServletHandler.
        AppServletHandler contextHandler = new AppServletHandler(ServletContextHandler.SESSIONS | ServletContextHandler.SECURITY);

        // Set Context Path. Usually "/"
        contextHandler.setContextPath("/");
        if (conf.getMaxFormContentSize() == 0) {
            // Default
            contextHandler.setMaxFormContentSize(conf.getMaxFormContentSize());
        }

        contextHandler.setErrorHandler(new BaseErrorHandler());
        contextHandler.setResourceBase(getResourceBase());
        contextHandler.addFilter(StaticFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        // Create servlet for Static Resources.
        ServletHolder defaultServletHolder = new ServletHolder(new DefaultServlet());
        defaultServletHolder.setInitParameter("dirAllowed", "true");
        defaultServletHolder.setInitParameter("baseResource", contextHandler.getResourceBase() );
        defaultServletHolder.setInitParameter("pathInfoOnly", "true");

        LOG.info("ResourceBase is " + contextHandler.getResourceBase() );

        // Add static files Servlet to Handler.
        contextHandler.addServlet(defaultServletHolder, STATIC_SERVLET_PATH + "/*");

        // Add App Servlet to Handler.
        contextHandler.addServlet(AppServlet.class, "/*");

        // Create Jetty Server
        int maxThreads = conf.getMaxThreads();
        int minThreads = conf.getMinThreads();
        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads);
        Server server = new Server(threadPool);

        // Set Http Port
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(conf.getPort());
        Connector[] connectors = new Connector[1];
        connectors[0] = connector;
        server.setConnectors(connectors);

        // Add Handlers to Server
        server.setHandler(contextHandler);

        return server;
    }

    /**
     * Returns StaticFile Root Dir.
     *
     *  At First, Search /static in Jar File(fat jar)
     *
     *  At Second, Search current Dir + src/main/resources/static
     */
    private String getResourceBase() {

        try {

                URL url = Thread.currentThread().getContextClassLoader().getResource(STATIC_FILES_DIR);

                if (url != null) {
                    return url.toString();
                }

            } catch (Exception e) {
                LOG.warn(e.getMessage());
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
                    File.separator;

        }

}

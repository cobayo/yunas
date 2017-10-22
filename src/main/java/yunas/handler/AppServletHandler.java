package yunas.handler;

import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Control All Servlets in Jetty.
 *
 */
public class AppServletHandler extends ServletContextHandler {

    public AppServletHandler(int options) {
        super(options);
    }
}

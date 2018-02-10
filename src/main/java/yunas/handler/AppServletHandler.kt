package yunas.handler

import org.eclipse.jetty.servlet.ServletContextHandler

/**
 * Control All Servlets in Jetty.
 *
 */
class AppServletHandler(options: Int) : ServletContextHandler(options)

package org.yunas.handler

import org.eclipse.jetty.servlet.ServletContextHandler

/**
 * Control All Servlets in Jetty.
 *
 * @author yosuke kobayashi
 */
class AppServletHandler(options: Int) : ServletContextHandler(options)

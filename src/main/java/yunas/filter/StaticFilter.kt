package yunas.filter

import yunas.server.JettyFactory
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import java.io.IOException


/**
 * Filter for routing static files.
 *
 * Get *.css,png,jpeg .. -> Forward to DefaultServlet.
 */
class StaticFilter:Filter {

    @Throws(ServletException::class)
    override fun init(filterConfig:FilterConfig) {
     // Nop
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request:ServletRequest, response:ServletResponse, chain:FilterChain) {

        val req = request as HttpServletRequest
        val path = req.requestURI

        // /*.css(js,jpg ..) forward /yunas_static_servlet/*.css
        for (e in StaticExtension.values()) {
            if (!path.startsWith(JettyFactory.staticServletPath) && path.endsWith(e.value)) {
                request.getRequestDispatcher(JettyFactory.staticServletPath + path).forward(request, response)
                return
            }
        }

        chain.doFilter(request, response)
    }

    override fun destroy() {
        // Nop
    }
}

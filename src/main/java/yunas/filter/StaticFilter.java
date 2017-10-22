package yunas.filter;

import yunas.server.JettyFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Filter for routing static files.
 *
 * Get *.css,png,jpeg .. -> Forward to DefaultServlet.
 */
public class StaticFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig)  {
     // Nop
    }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

       HttpServletRequest req = (HttpServletRequest)request;
        String path = req.getRequestURI();

        // /*.css(js,jpg ..) forward /yunas_static_servlet/*.css
        for (StaticExtension e : StaticExtension.values()) {
            if (!path.startsWith(JettyFactory.getStaticServletPath()) && path.endsWith(e.getValue())) {
                request.getRequestDispatcher(JettyFactory.getStaticServletPath() + path).forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nop
    }
}

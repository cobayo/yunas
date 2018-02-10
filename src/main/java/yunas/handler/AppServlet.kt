package yunas.handler

import yunas.exception.YunasExceptionProvider
import yunas.handler.Execute.execute
import yunas.http.HttpMethod
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Handle All Requests.
 *
 * @author Yosuke Kobayashi
 */
class AppServlet : HttpServlet() {


    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest,
                              response: HttpServletResponse) {

        execute(request, response, HttpMethod.GET)

    }

    @Throws(ServletException::class, IOException::class)
    override fun doHead(req: HttpServletRequest, resp: HttpServletResponse) {

        execute(req, resp, HttpMethod.HEAD)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {

        execute(req, resp, HttpMethod.POST)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {

        execute(req, resp, HttpMethod.PUT)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {

        execute(req, resp, HttpMethod.DELETE)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doOptions(req: HttpServletRequest, resp: HttpServletResponse) {

        execute(req, resp, HttpMethod.OPTIONS)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doTrace(req: HttpServletRequest, resp: HttpServletResponse) {
        // No Support
        throw YunasExceptionProvider().notFoundPath(req.pathInfo)
    }

}
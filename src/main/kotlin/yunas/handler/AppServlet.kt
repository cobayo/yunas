package yunas.handler

import yunas.exception.YunasExceptionProvider
import yunas.http.HttpMethod

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

import yunas.handler.Execute.execute

/**
 * Handle All Requests.
 *
 * @author Yosuke Kobayashi
 */
class AppServlet : HttpServlet() {


    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest,
                       response: HttpServletResponse) {

        execute(request, response, HttpMethod.GET)

    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest,
                        response: HttpServletResponse) {

        execute(request, response, HttpMethod.POST)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPut(request: HttpServletRequest,
                       response: HttpServletResponse) {

        execute(request, response, HttpMethod.PUT)

    }

    @Throws(ServletException::class, IOException::class)
    override fun doDelete(request: HttpServletRequest,
                          response: HttpServletResponse) {

        execute(request, response, HttpMethod.DELETE)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doHead(request: HttpServletRequest, response: HttpServletResponse) {
        execute(request, response, HttpMethod.HEAD)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doTrace(request: HttpServletRequest, response: HttpServletResponse) {
        // NoSupport.
        throw YunasExceptionProvider().notFoundPath(request.pathInfo)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doOptions(request: HttpServletRequest, response: HttpServletResponse) {

        execute(request, response, HttpMethod.OPTIONS)

    }
}
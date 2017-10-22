package yunas.handler

import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.ErrorHandler
import yunas.ErrorController
import yunas.ModelAndView
import yunas.Yunas
import yunas.http.DefaultContentType
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Handling Errors.
 * if Not Set ErrorController in Yunas, Call createDefaultResponse method.
 *
 * @author yosuke kobayashi
 */
class BaseErrorHandler : ErrorHandler() {

    @Throws(IOException::class)
    override fun handle(target: String?, baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse) {

        baseRequest.isHandled = true
        val handler = Yunas.errorHandler
        if (handler != null) {
            createCustomResponse(handler, request, response)
        } else {
            createDefaultResponse(baseRequest, request, response)
        }

    }

    @Throws(IOException::class)
    private fun createCustomResponse(controller: ErrorController, request: HttpServletRequest, response: HttpServletResponse) {

        val obj = controller.invoke(request, response.status)

        if (obj is ModelAndView) {
            response.contentType = DefaultContentType.HTML.value
            val templateEngine = Yunas.templateEngine
            response.writer.print(templateEngine.render(obj))
            return
        }

        response.writer.print(obj.toString())

    }

    @Throws(IOException::class)
    private fun createDefaultResponse(baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse) {

        if (baseRequest.contentType == null || baseRequest.contentType == DefaultContentType.HTML.value) {
            response.contentType = DefaultContentType.HTML.value
            createErrorHtml(request, response)
        } else {
            response.writer.print(response.status)
        }
    }

    @Throws(IOException::class)
    private fun createErrorHtml(request: HttpServletRequest, response: HttpServletResponse) {

        response.writer.append("<html><head><title>Error</title></head><body>")
                .append("Error(Status Code: ").append(response.status.toString())
                .append(")").append("</body><html>").close()
    }
}

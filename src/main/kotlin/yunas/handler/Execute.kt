package yunas.handler

import org.slf4j.LoggerFactory
import yunas.Context
import yunas.Json
import yunas.ModelAndView
import yunas.Yunas
import yunas.exception.YunasExceptionProvider
import yunas.exception.YunasNotFoundException
import yunas.http.DefaultContentType
import yunas.http.HttpMethod
import yunas.util.BaseUtil
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Find Controller and Create Response.
 *
 * @author yosuke kobayashi
 */
internal object Execute {

    private val LOG = LoggerFactory.getLogger(Execute::class.java)

    @Throws(IOException::class)
    fun execute(request: HttpServletRequest, response: HttpServletResponse, method: HttpMethod) {

        try {
            // Find Controller
            val route = Yunas.router.find(method, request.requestURI)

            // Create Context
            val context = Context(request, response, route)

            // Default Headers And Status.
            context.response.contentType = route.defaultContentType.value
            context.response.status = HttpServletResponse.SC_OK

            // Execute Interceptor Before Controller
            Yunas.before?.invoke(context)

            // Call Controller Method.
            val obj = route.getController().invoke(context)

            // Create Response
            bindResponse(context, obj)

            // Execute Interceptor After Controller
            Yunas.after?.invoke(context)

        } catch (e: YunasNotFoundException) {
            response.sendError(404)
        } catch (e: Exception) {
            LOG.error(e.message)
            e.printStackTrace()
            response.sendError(500)
        }

    }

    @Throws(Exception::class)
    private fun bindResponse(context: Context, obj: Any?) {


        if (obj == null) {
            // When obj is null, Return Empty Only.
            context.response.writer?.print("")
            return
        }

        if (obj is String) {

            // If Return value is String, Always Add String to Response.(Not depends on Content-Type)
            context.response.writer?.print(obj.toString())
            return
        }


        if (context.route.defaultContentType === DefaultContentType.JSON) {

            // If ContentType is JSON and Return Any obj (except String), Convert JSON String.
            context.response.writer?.print(Json.toJson(obj))
            return
        }

        if (context.route.defaultContentType === DefaultContentType.HTML) {
            // If ContentType is HTML And Return Value is Not String, Do Use Template Engine
            if (Yunas.templateEngine == null) {
                throw YunasExceptionProvider().notFoundTemplateEngine()
            }

            if (obj is ModelAndView) {
                context.response.writer?.print(Yunas.templateEngine.render((obj as ModelAndView?)!!))
                return
            }

            // Bind ModelAndView, When ContentType is HTML, Return Map And set viewName to Context.
            if (!BaseUtil.blank(context.viewName)) {

                if (obj is Map<*, *>) {
                    context.response.writer?.print(Yunas.templateEngine.render(ModelAndView((obj as Map<*, *>?)!!, context.viewName!!)))
                    return
                }

            }

        }

        throw YunasExceptionProvider().unsupportedReturnType()
    }
}

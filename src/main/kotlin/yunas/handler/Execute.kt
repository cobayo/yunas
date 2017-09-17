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
    private fun bindResponse(context: Context, `object`: Any?) {


        if (`object` == null) {
            // When object is null, Return Empty Only.
            context.response.writer?.print("")
            return
        }

        if (`object` is String) {

            // Add String to Response.
            context.response.writer?.print(`object`.toString())
            return
        }


        if (context.route.defaultContentType === DefaultContentType.JSON) {

            // If ContentType is JSON and Return Any object (except String), Convert JSON String.
            context.response.writer?.print(Json.toJson(`object`))
            return
        }

        if (context.route.defaultContentType === DefaultContentType.HTML) {

            if (`object` is ModelAndView) {

                // Do Use Template Engine
                val templateEngine = Yunas.templateEngine
                context.response.writer?.print(templateEngine.render((`object` as ModelAndView?)!!))
                return
            }

            // Bind ModelAndView, When ContentType is HTML, Return Map And set viewName to Context.
            if (!BaseUtil.blank(context.viewName)) {

                val templateEngine = Yunas.templateEngine
                if (`object` is Map<*, *>) {
                    context.response.writer?.print(templateEngine.render(ModelAndView((`object` as Map<*, *>?)!!, context.viewName!!)))
                    return
                }

            }

        }

        throw YunasExceptionProvider().unsupportedReturnType()
    }
}

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
import yunas.http.YunasSession
import yunas.router.Route
import yunas.util.BaseUtil
import yunas.util.YunasSessionCookieBaker
import java.io.IOException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Find Controller and Create Response.
 *
 */
internal object Execute {

    private val LOG = LoggerFactory.getLogger(Execute::class.java)

    @Throws(IOException::class)
    fun execute(request: HttpServletRequest, response: HttpServletResponse, method: HttpMethod) {

        try {
            // Find Controller
            val route = Yunas.router.find(method, request.requestURI)

            // Create Context
            val context = createContext(request, response, route)

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

            // Bake Signed Cookie(Yunas Session)
            bindYunasSession(response,context)

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


    private fun createContext (req : HttpServletRequest,res: HttpServletResponse,route: Route) : Context {

        val yunasCookieSession = req.cookies?.filter { it.name == Yunas.configuration?.sessionKey }
        val data : MutableMap<String,String> = if (yunasCookieSession == null || yunasCookieSession.isEmpty()) {
            mutableMapOf()
        } else {
            YunasSessionCookieBaker.decode(yunasCookieSession.get(0).value)
        }

        return Context(req,res,route, YunasSession(data))


    }

    private fun bindYunasSession(res:HttpServletResponse,context: Context) {

        val value = YunasSessionCookieBaker.encode(context.yunasSession.data)
        val cookie = Cookie(Yunas.configuration?.sessionKey,value)

        cookie.maxAge = Yunas.configuration!!.sessionMaxAge

        res.addCookie(cookie)

    }





}

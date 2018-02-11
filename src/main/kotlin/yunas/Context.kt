package yunas

import yunas.http.YunasSession
import yunas.router.Route

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.LinkedHashMap

/**
 * Context that has HttpServletRequest, HttpServletResponse and routing information.
 *
 * @author yosuke kobayashi
 */
open class Context
/**
 * Constructor.
 * params are not null.
 * @param request HttpServletRequest
 * @param response HttpServletResponse
 * @param route Route
 */
(
        /**
         * * Returns the current HttpServletRequest.
         *
         */
        val request: HttpServletRequest,
        /**
         * Current http response.
         * Returns the current HttpServletResponse.
         */
        val response: HttpServletResponse,
        /**
         * Routing Information.
         * Returns the current Routing Information.
         *
         */
        val route: Route, val yunasSession: YunasSession) {

    /**
     * Template View Name, When not using ModelAndView.
     * Returns a ViewName of template engines
     *
     */
    val viewName: String? = null

    /**
     * User's Additional Data.
     */
    private val extra: LinkedHashMap<String, Any>? = null


    fun setStatusCode(sc: Int) {
        response.status = sc
    }

    /**
     * Set Any ContentType into Response.
     *
     * @param contentType String
     */
    fun setContentType(contentType: String) {
        this.response.contentType = contentType
    }


    /**
     * Returns additional data.
     */
    fun getExtra(): Map<String, Any>? {
        return extra
    }

    /**
     * Set additional data.
     */
    fun putExtra(name: String, value: Any) {
        this.extra!!.put(name, value)
    }
}

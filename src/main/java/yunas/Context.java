package yunas;

import yunas.http.YunasSession;
import yunas.router.Route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Context that has HttpServletRequest, HttpServletResponse and routing information.
 *
 * @author yosuke kobayashi
 */
class Context {

   /**
    * * Returns the current HttpServletRequest.
    *
    */
    private HttpServletRequest request;

    /**
     * Current http response.
     * Returns the current HttpServletResponse.
     */
    private HttpServletResponse response;
    /**
     * Routing Information.
     * Returns the current Routing Information.
     *
     */
    private Route route;

    private YunasSession yunasSession;

    /**
     * Template View Name, When not using ModelAndView.
     * Returns a ViewName of template engines
     *
     */
    private String viewName;

    /**
     * User's Additional Data.
     */
    private LinkedHashMap<String,Object> extra;

    /**
     * Constructor.
     * params are not null.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param route Route
     */
    public Context(HttpServletRequest request, HttpServletResponse response, Route route, YunasSession yunasSession) {

        this.request = request;
        this.response = response;
        this.route = route;
        this.yunasSession = yunasSession;

    }


    public void setStatusCode(int sc) {
        response.setStatus(sc);
    }

    /**
     * Set Any ContentType into Response.
     *
     * @param contentType String
     */
    public void setContentType(String contentType) {
        this.response.setContentType(contentType);
    }


    /**
     * Returns additional data.
     */
    public Map<String,Object> getExtra() {
        return extra;
    }

    /**
     * Set additional data.
     */
    public void  putExtra(String name, Object value) {
        this.extra.put(name, value);
    }

}

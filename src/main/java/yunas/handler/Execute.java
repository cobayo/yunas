package yunas.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.Context;
import yunas.Json;
import yunas.ModelAndView;
import yunas.Yunas;
import yunas.exception.YunasExceptionProvider;
import yunas.exception.YunasNotFoundException;
import yunas.http.DefaultContentType;
import yunas.http.HttpMethod;
import yunas.http.YunasSession;
import yunas.router.Route;
import yunas.util.BaseUtil;
import yunas.util.YunasSessionCookieBaker;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Find Controller and Create Response.
 *
 */
public class Execute {

    private static final Logger LOG = LoggerFactory.getLogger(Execute.class);

    public static void execute(HttpServletRequest request, HttpServletResponse response, HttpMethod method) throws IOException {

        try {
            // Find Controller
            Route route = Yunas.getRouter().find(method, request.getRequestURI());

            // Create Context
            Context context = createContext(request, response, route);

            // Default Headers And Status.
            context.getResponse().setContentType(route.getDefaultContentType().getValue());
            context.getResponse().setStatus(HttpServletResponse.SC_OK);

            // Execute Interceptor Before Controller
            if (Yunas.getBefore() != null) {
                Yunas.getBefore().action(context);
            }

            // Call Controller Method.
            Object obj = route.getController().action(context);

            // Create Response
            bindResponse(context, obj);

            // Execute Interceptor After Controller
            if (Yunas.getAfter() != null) {
                Yunas.getAfter().action(context);
            }

            // Bake Signed Cookie(Yunas Session)
            bindYunasSession(response,context);

        } catch (YunasNotFoundException e) {
            response.sendError(404);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            response.sendError(500);
        }

    }

    private static void bindResponse(Context context, Object obj) throws  Exception {


        if (obj == null) {
            // When obj is null, Return Empty Only.
            context.getResponse().getWriter().print("");
            return;
        }

        if (obj instanceof String) {

            // If Return value is String, Always Add String to Response.(Not depends on Content-Type)
            context.getResponse().getWriter().print(obj.toString());
            return;
        }


        if (context.getRoute().getDefaultContentType() == DefaultContentType.JSON) {

            // If ContentType is JSON and Return Any obj (except String), Convert JSON String.
            context.getResponse().getWriter().print(new Json().toJson(obj));
            return;
        }

        if (context.getRoute().getDefaultContentType() == DefaultContentType.HTML) {
            // If ContentType is HTML And Return Value is Not String, Do Use Template Engine
            if (Yunas.getTemplateEngine() == null) {
                throw new YunasExceptionProvider().notFoundTemplateEngine();
            }

            if (obj instanceof ModelAndView) {
                context.getResponse().getWriter().print(Yunas.getTemplateEngine().render((ModelAndView)obj));
                return;
            }

            // Bind ModelAndView, When ContentType is HTML, Return Map And set viewName to Context.
            if (!BaseUtil.blank(context.getViewName())) {

                if (obj instanceof  Map<?, ?>) {
                    context.getResponse().getWriter().print(Yunas.getTemplateEngine().render(new ModelAndView((Map<?, ?>)obj,context.getViewName())));
                    return;
                }

            }

        }

        throw new YunasExceptionProvider().unsupportedReturnType();
    }


    private static Context createContext (HttpServletRequest req ,HttpServletResponse res,Route route) {

        Cookie yunasCookieSession = Arrays.stream(req.getCookies()).filter( c -> BaseUtil.equals(c.getName(),Yunas.getConfiguration().getSessionKey())).findFirst().orElse(null);
        Map<String,String> data = new HashMap<>();
        if (yunasCookieSession != null) {

            YunasSessionCookieBaker.decode(yunasCookieSession.getValue());
        }

        return new Context(req,res,route, new YunasSession(data));


    }

    private static void bindYunasSession(HttpServletResponse res,Context context) {

        String value = YunasSessionCookieBaker.encode(context.getYunasSession().getData());
        Cookie cookie = new Cookie(Yunas.getConfiguration().getSessionKey(),value);

        cookie.setMaxAge(Yunas.getConfiguration().getSessionMaxAge());

        res.addCookie(cookie);

    }





}

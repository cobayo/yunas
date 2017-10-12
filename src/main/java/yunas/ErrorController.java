package yunas;

import kotlin.jvm.functions.Function2;

import javax.servlet.http.HttpServletRequest;

/**
 * ErrorController Interface for Java.
 *
 */
public interface ErrorController extends Function2<HttpServletRequest, Integer,Object> {

    public Object action(HttpServletRequest req, Integer integer);

    @Override
    default Object invoke(HttpServletRequest req, Integer integer) {
        return action(req, integer);

    }
}

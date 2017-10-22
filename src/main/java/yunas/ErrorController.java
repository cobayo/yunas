package yunas;

import javax.servlet.http.HttpServletRequest;
import java.util.function.BiFunction;

/**
 * ErrorController Interface for Java.
 *
 */
public interface ErrorController extends BiFunction<HttpServletRequest, Integer,Object> {

    public Object action(HttpServletRequest req, Integer integer);

    @Override
    default Object apply(HttpServletRequest req, Integer integer) {
        return action(req, integer);

    }
}

package yunas

import javax.servlet.http.HttpServletRequest
import java.util.function.BiFunction

/**
 * ErrorController Interface for Java.
 *
 */
@FunctionalInterface
interface ErrorController : BiFunction<HttpServletRequest, Int, Any> {

    fun action(req: HttpServletRequest, integer: Int?): Any

    override fun apply(req: HttpServletRequest, integer: Int): Any {
        return action(req, integer)

    }
}

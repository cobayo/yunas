package yunas

import java.util.function.Function

/**
 * yunas.Controller interface for java.
 */
@FunctionalInterface
interface Controller : Function<Context, Any> {

    fun action(context: Context): Any

    override fun apply(context: Context): Any {
        return action(context)

    }
}

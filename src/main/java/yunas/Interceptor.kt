package yunas

import java.util.function.Consumer

/**
 * Interceptor interface for Java.
 */
@FunctionalInterface
interface Interceptor : Consumer<Context> {

    fun action(context: Context)

    override fun accept(context: Context) {
        action(context)
    }
}

package yunas

import java.util.function.Consumer

/**
 * Batch Interface for Java.
 */
@FunctionalInterface
interface Batch : Consumer<Array<String>> {

    fun action(args: Array<String>)

    override fun accept(args: Array<String>) {
        action(args)
    }
}

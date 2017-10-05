package yunas.batch

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import yunas.Batch
import kotlin.reflect.KProperty
import kotlin.system.exitProcess

/**
 * Manage Batch Modules.
 *
 */
open class DefaultBatchManager :BatchManager {

    private val modules:MutableMap<String,Batch> = mutableMapOf()

    override fun add(name:String,executable:Batch) {
        modules.put(name,executable)
    }

    override fun run(args:Array<String>) {

        try {
            val name = System.getProperty("yunas.batch")
            modules.get(name)?.invoke(args)
        } catch (e: Exception) {
             LOGGER.error(e.message)
             exitProcess(1)
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): DefaultBatchManager = DefaultBatchManager()

    companion object {

        val LOGGER:Logger = getLogger(javaClass)

    }

}
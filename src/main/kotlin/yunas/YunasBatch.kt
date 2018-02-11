package yunas

import yunas.batch.DefaultBatchManager
import yunas.configuration.Configuration

/**
 * Entry Point of Yunas Framework For DefaultBatchManager.
 *
 */
object YunasBatch {

    private val manager = DefaultBatchManager()

    private val instance = App.initBatch()

    val configuration: Configuration?
        get() = instance.configuration

    @JvmStatic
    fun run(args: Array<String>) {
        manager.run(args)
    }

    fun add(name: String, executable: Batch) {
        manager.add(name, executable)
    }

}
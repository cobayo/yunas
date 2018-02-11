package yunas.batch


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import yunas.Batch

import java.util.HashMap

import java.lang.System.exit

/**
 * Manage Batch Modules.
 *
 */
class DefaultBatchManager : BatchManager {

    private val modules = HashMap<String, Batch>()

    override fun add(name: String, executable: Batch) {
        modules.put(name, executable)
    }

    override fun run(args: Array<String>) {

        try {
            val name = System.getProperty("yunas.batch")
            if (!modules.containsKey(name)) {
                LOG.error("NOT FOUND module: " + name)
                return
            }
            modules[name]!!.action(args)
        } catch (e: Exception) {
            LOG.error(e.message)
            exit(1)
        }

    }

    companion object {

        private val LOG = LoggerFactory.getLogger(DefaultBatchManager::class.java)
    }

}
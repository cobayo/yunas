package yunas.batch

import yunas.Batch

/**
 * BatchManager.
 */
interface BatchManager {

    fun add(name: String, executable: Batch)

    fun run(args: Array<String>)
}
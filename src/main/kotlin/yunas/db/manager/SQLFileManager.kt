package yunas.db.manager


import org.slf4j.LoggerFactory
import yunas.util.LruCache
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Manage an outside sql file.
 *
 */
object SQLFileManager {

    private val LOG = LoggerFactory.getLogger(SQLFileManager::class.java)

    private val CACHE_SIZE = 5 * 1024 * 1024 / 4

    /* Cache in Memory */
    private val cache = object : LruCache<String, String>(CACHE_SIZE) {

        /**
         * {@inheritDoc}
         */
        override fun sizeOf(key: String, value: String?): Int {
            return value!!.length
        }

    }

    /**
     * Read a sql from an outside file in src/main/resources.
     *
     * @param fileName    SQLFile Name. "sql/xxxx.sql"
     * @return    SQL
     */
    fun readSql(fileName: String): String? {

        // Create a cache key
        val key = createKey(fileName)

        var sql = cache.get(key)

        // If exists a sql in cache, Returns it.
        if (sql != null) {
            return sql
        }

        try {
            SQLFileManager::class.java.classLoader.getResourceAsStream(fileName).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream, "UTF-8")).use { br ->

                    val response = StringBuilder()

                    var len:Int
                    val buff = CharArray(4096)
                    while (true) {
                        len = br.read(buff, 0, 4096)
                        if (len <= -1) {
                            break
                        }
                        response.append(buff, 0, len)
                    }

                    sql = response.toString()

                }
            }
        } catch (e: Exception) {
            LOG.error(e.message)
        }

        if (sql != null) {

            sql = sql!!.replace("\r\n".toRegex(), " ").replace("\n".toRegex(), " ").replace("\r".toRegex(), " ")

            cache.put(key, sql)

        } else {

            LOG.error("SQL-file not found.[$fileName]")

        }

        return sql

    }

    /**
     * Create Cache key.
     *
     */
    private fun createKey(fileName: String): String {

        val sb = StringBuilder()
        sb.append("sql#")
        sb.append(fileName)

        return sb.toString()

    }

}
/**
 * private constructor.
 */
// Nop
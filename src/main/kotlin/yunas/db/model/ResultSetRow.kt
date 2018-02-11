package yunas.db.model

import org.slf4j.LoggerFactory
import yunas.util.BaseUtil
import java.math.BigDecimal
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.*

/**
 * Row of DB Data by SQL(Select).
 * ThreadSafe.
 */
class ResultSetRow(// Data from DB
        private val data: Map<String, Any>?) {

    /**
     * Check if Key exists.
     *
     * @param key String
     * @return    Not NUll = true
     */
    private fun checkKey(key: String): Boolean {

        if (data == null) {
            return false
        }

        return if (!data.containsKey(key)) {
            false
        } else data[key] != null

    }


    /**
     * Get All Keys of data.
     * @return not null.
     */
    fun keys(): Set<String> {
        return data!!.keys
    }

    /**
     * get value as Object.
     * a return value is nullable.
     */
    operator fun get(key: String): Any? {

        return if (!checkKey(key)) {
            null
        } else {
            data!![key]
        }
    }


    fun getString(key: String): String? {


        if (!checkKey(key)) {
            return ""
        }

        val obj = data!![key]

        if (obj is String) {
            return obj.toString()
        }

        try {

            return if (obj is Date) {

                SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(obj)
            } else BaseUtil.toString(obj)

        } catch (e: Exception) {
            LOG.debug(e.message)
            return ""
        }

    }

    fun getInt(key: String): Int {

        if (!checkKey(key)) {
            return 0
        }

        val obj = data!![key]

        if (obj is Int) {
            return obj
        }

        try {
            return Integer.parseInt(BaseUtil.toString(obj)!!)
        } catch (e: Exception) {
            LOG.debug(e.message)
            return 0
        }

    }

    fun getLong(key: String): Long {

        if (!checkKey(key)) {
            return 0
        }

        val obj = data!![key]

        if (obj is Long) {
            return obj
        }

        try {

            return java.lang.Long.parseLong(BaseUtil.toString(obj)!!)
        } catch (e: Exception) {
            LOG.debug(e.message)
            return 0
        }

    }

    fun getDouble(key: String): Double? {

        if (!checkKey(key)) {
            return 0.0
        }

        val obj = data!![key]

        if (obj is Double) {
            return obj
        }

        try {

            return java.lang.Double.parseDouble(BaseUtil.toString(data[key])!!)
        } catch (e: Exception) {
            LOG.debug(e.message)
            return 0.0
        }

    }

    fun getBigDecimal(key: String): BigDecimal? {

        if (!checkKey(key)) {
            return BigDecimal("0")
        }

        val obj = data!![key] ?: return null

        if (obj is BigDecimal) {
            return obj
        }

        try {

            return BigDecimal(BaseUtil.toString(obj)!!)
        } catch (e: Exception) {
            LOG.debug(e.message)
            return BigDecimal("0")
        }

    }

    companion object {

        // Logger for debug
        private val LOG = LoggerFactory.getLogger(ResultSetRow::class.java)


        /**
         * Convert ResultSet to ResultSetRow Instance.
         */
        fun convert(resultSet: ResultSet): ResultSetRow? {

            try {

                val map = LinkedHashMap<String, Any>()
                if (!resultSet.next()) {
                    return ResultSetRow(map)
                }

                val metaData = resultSet.metaData


                for (i in 0..metaData.columnCount - 1) {

                    val key = metaData.getColumnLabel(i + 1)
                    val value = resultSet.getObject(i + 1)

                    map.put(key, value)

                }

                return ResultSetRow(map)

            } catch (ex: Exception) {

                LOG.error(ex.message)
                return null

            }

        }

        fun convertToList(resultSet: ResultSet): List<ResultSetRow?> {


            try {

                val list = ArrayList<ResultSetRow?>()

                while (resultSet.next()) {
                    list.add(ResultSetRow.convert(resultSet))
                }

                return list

            } catch (e: Exception) {
                LOG.debug(e.message)
                return mutableListOf()
            } finally {
                BaseUtil.closeAC(resultSet)
            }

        }
    }


}

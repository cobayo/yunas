package org.yunas.util

import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.*

/**
 * Bean of row of DB Data by SQL(Select).
 * ThreadSafe.
 */
open class ResultSetRow// Constructor
(// Row Object From Databases.
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

        if (!data.containsKey(key)) {
            return false
        }

        return data[key] != null

    }


    /**
     * Get All Keys of data.
     * @return not null.
     */
    fun keys(): Set<String> {

        return data?.keys ?: HashSet()

    }

    /**
     * get value as Object.
     * a return value is nullable.
     */
    operator fun get(key: String): Any? {

        return if (!checkKey(key)) {
            null
        } else data!![key]

    }


    fun getString(key: String): String? {


        if (!checkKey(key)) {
            return ""
        }

        val obj = data!![key]

        if (obj is String) {
            return obj
        }

        try {

            if (obj is Date) {

               return SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(obj)
            }

            return BaseUtil.toString(data[key])

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

            return Integer.parseInt(BaseUtil.toString(data[key])!!)
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

            return java.lang.Long.parseLong(BaseUtil.toString(data[key])!!)
        } catch (e: Exception) {
            LOG.debug(e.message)
            return 0
        }

    }

    fun getDouble(key: String): Double {

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

    fun getBigDecimal(key: String): BigDecimal {

        if (!checkKey(key)) {
            return BigDecimal("0")
        }

        val obj = data!![key]

        if (obj is BigDecimal) {
            return obj
        } else {
            when (obj) {
                is Int -> return BigDecimal(obj)
                is Double -> return BigDecimal(obj)
                is Long -> return BigDecimal(obj)
                else -> {
                }
            }
        }

        return try {
            BigDecimal(BaseUtil.toString(obj)!!)
        } catch (e: Exception) {
            LOG.debug(e.message)
            BigDecimal("0")
        }

    }

    companion object {

        // Logger for debug
        private val LOG = LoggerFactory.getLogger(ResultSetRow::class.java)

        /**
         * Convert ResultSet to ResultSetRow Instance.
         */
        fun convert(resultSet: ResultSet): ResultSetRow {


            try {

                val map = LinkedHashMap<String, Any>()

                val metaData = resultSet.metaData
                if (!resultSet.next()) {
                    return ResultSetRow(map)
                }

                for (i in 0 until metaData.columnCount) {

                    val key = metaData.getColumnLabel(i + 1)
                    val value = resultSet.getObject(i + 1)

                    map.put(key, value)

                }

                return ResultSetRow(map)

            } catch (e: Exception) {
                LOG.debug(e.message)
                return ResultSetRow(emptyMap())
            } finally {
                BaseUtil.closeAC(resultSet)
            }

        }

        fun convertToList(resultSet: ResultSet): List<ResultSetRow>? {


            try {

                val list = ArrayList<ResultSetRow>()

                while (resultSet.next()) {
                    list.add(ResultSetRow.convert(resultSet))
                }

                return list

            } catch (e: Exception) {
                LOG.debug(e.message)
                return null
            } finally {
                BaseUtil.closeAC(resultSet)
            }

        }
    }

}

package yunas.util

import org.slf4j.LoggerFactory
import java.io.Closeable
import java.lang.reflect.Array

/**
 * Yunas Framework Utils.
 *
 * @author Yosuke Kobayashi
 */
object BaseUtil {

    private val LOG = LoggerFactory.getLogger(BaseUtil::class.java)

    /**
     * Check if String is NULL or Empty.
     *
     * @param value String
     * @return boolean true is Empty or Null
     */
    fun blank(value: String?): Boolean {

        return value == null || value.isEmpty()

    }

    /**
     * Convert to Integer.
     *
     * @param value String
     */
    fun parseInt(value: String?): Int {

        if (value == null) {
            return 0
        }

        try {

            return Integer.parseInt(value)

        } catch (e: Exception) {
            return 0
        }

    }


    /**
     * Convert to Long.
     * @param value String
     */
    fun parseLong(value: String?): Long {

        if (value == null) {
            return 0
        }

        try {

            return java.lang.Long.parseLong(value)

        } catch (e: Exception) {
            return 0
        }

    }

    /**
     * Check If Equals Strings.
     * @param value String
     * @param anotherValue String
     */
    fun equals(value: String?, anotherValue: String?): Boolean {
        return if (value == null) anotherValue == null else value == anotherValue
    }

    /**
     * Check If Equals Strings, Ignore Case.
     * @param value String
     * @param anotherValue String
     */
    fun equalsIgnorecase(value: String?, anotherValue: String?): Boolean {
        return value?.equals(anotherValue!!, ignoreCase = true) ?: (anotherValue == null)
    }

    fun toString(value: Any?): String? {
        var value: Any? = value ?: return null

        if (value!!.javaClass.isArray && Array.getLength(value) > 0) {

            value = Array.get(value, 0)

            if (value == null) {
                return null
            }

        }

        return value.toString()

    }

    fun close(vararg closeables: Closeable?) {

        try {

            for (c in closeables) {
                c!!.close()
            }

        } catch (e: Exception) {
            LOG.info(e.message)
        }

    }

    fun closeAC(vararg closeables: AutoCloseable?) {

        try {

            for (c in closeables) {
                c!!.close()
            }

        } catch (e: Exception) {
            LOG.info(e.message)
        }

    }
}

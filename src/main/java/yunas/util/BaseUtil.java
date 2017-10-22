package yunas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.lang.reflect.Array;

/**
 * Yunas Framework Utils.
 *
 * @author Yosuke Kobayashi
 */
public class BaseUtil {

    private static final Logger LOG = LoggerFactory.getLogger(BaseUtil.class);

    /**
     * Check if String is NULL or Empty.
     *
     * @param value String
     * @return boolean true is Empty or Null
     */
    public static boolean blank(String value) {

        return value == null || value.isEmpty();

    }

    /**
     * Convert to Integer.
     *
     * @param value String
     */
    public int parseInt(String value) {

        if (value == null) {
            return 0;
        }

        try {

            return  Integer.parseInt(value);

        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * Convert to Long.
     * @param value String
     */
    public static long parseLong(String value) {

        if (value == null) {
            return 0;
        }

        try {

            return  java.lang.Long.parseLong(value);

        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * Check If Equals Strings.
     * @param value String
     * @param anotherValue String
     */
    public static boolean equals(String value, String anotherValue) {
        return  (value == null) ? anotherValue == null : value.equals(anotherValue);
    }

    /**
     * Check If Equals Strings, Ignore Case.
     * @param value String
     * @param anotherValue String
     */
    public static boolean equalsIgnorecase(String value, String anotherValue) {
        return  (value == null) ? anotherValue == null : value.equalsIgnoreCase(anotherValue);
    }

    public static String toString(Object value) {

        if (value == null) {
            return null;
        }

        if (value.getClass().isArray() && Array.getLength(value) > 0) {

            value = Array.get(value, 0);

            if (value == null) {
                return null;
            }

        }

        return value.toString();

    }

    public static void close(Closeable... closeables) {

        try {

            for (Closeable c : closeables) {
                c.close();
            }

        } catch (Exception e) {
            LOG.info(e.getMessage());
        }

    }

    public static void closeAC(AutoCloseable... closeables) {

        try {

            for (AutoCloseable c : closeables) {
                c.close();
            }

        } catch (Exception e) {
            LOG.info(e.getMessage());
        }

    }
}

package yunas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Bean of row of DB Data by SQL(Select).
 * ThreadSafe.
 */
public class ResultSetRow {

    // Logger for debug
    private static final Logger LOG = LoggerFactory.getLogger(ResultSetRow.class);

    // Data from DB
    private Map<String, Object> data;

    public ResultSetRow(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Check if Key exists.
     *
     * @param key String
     * @return    Not NUll = true
     */
    private boolean checkKey(String key) {

        if (data == null) {
            return false;
        }

        if (!data.containsKey(key)) {
            return false;
        }

        return data.get(key) != null;

    }


    /**
     * Get All Keys of data.
     * @return not null.
     */
    public Set<String> keys() {
        return data.keySet();
    }

    /**
     * get value as Object.
     * a return value is nullable.
     */
    public Object get(String key){

        if (!checkKey(key)) {
            return null;
        } else {
            return data.get(key);
        }
    }


    public String getString(String key) {


        if (!checkKey(key)) {
            return "";
        }

        Object obj = data.get(key);

        if (obj instanceof String) {
            return obj.toString();
        }

        try {

            if (obj instanceof Date) {

               return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(obj);
            }

            return BaseUtil.toString(obj);

        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return "";
        }

    }

    public int getInt(String key){

        if (!checkKey(key)) {
            return 0;
        }

        Object obj = data.get(key);

        if (obj instanceof Integer) {
            return (int)obj;
        }

        try {
            return Integer.parseInt(BaseUtil.toString(obj));
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return 0;
        }

    }

    public long getLong(String key) {

        if (!checkKey(key)) {
            return 0;
        }

        Object obj = data.get(key);

        if (obj instanceof Long) {
            return (long)obj;
        }

        try {

            return java.lang.Long.parseLong(BaseUtil.toString(obj));
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return 0;
        }

    }

    public Double getDouble(String key) {

        if (!checkKey(key)) {
            return 0.0;
        }

        Object obj = data.get(key);

        if (obj instanceof Double) {
            return (double)obj;
        }

        try {

            return java.lang.Double.parseDouble(BaseUtil.toString(data.get(key)));
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return 0.0;
        }

    }

    public BigDecimal getBigDecimal(String key) {

        if (!checkKey(key)) {
            return new BigDecimal("0");
        }

        Object obj = data.get(key);

        if (obj == null) {
            return null;
        }

        if (obj instanceof BigDecimal) {
            return (BigDecimal)obj;
        }

         try {

             return  new BigDecimal(BaseUtil.toString(obj));
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return new BigDecimal("0");
        }

    }



        /**
         * Convert ResultSet to ResultSetRow Instance.
         */
        public static ResultSetRow convert(ResultSet resultSet) {

            try {

                Map<String, Object> map = new LinkedHashMap<>();
                if (!resultSet.next()) {
                    return new ResultSetRow(map);
                }

                ResultSetMetaData metaData = resultSet.getMetaData();


                for (int i = 0; i < metaData.getColumnCount(); i++) {

                    String key = metaData.getColumnLabel(i + 1);
                    Object value = resultSet.getObject(i + 1);

                    map.put(key, value);

                }

                return new ResultSetRow(map);

            } catch (Exception ex) {

                LOG.error(ex.getMessage());
                return null;

            }

        }

        public static List<ResultSetRow>  convertToList(ResultSet resultSet) {


            try {

                List<ResultSetRow> list = new ArrayList<>();

                while (resultSet.next()) {
                    list.add(ResultSetRow.convert(resultSet));
                }

                return list;

            } catch (Exception e) {
                LOG.debug(e.getMessage());
                return null;
            } finally {
                BaseUtil.closeAC(resultSet);
            }

        }


}

package yunas.db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.util.LruCache;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Manage an outside sql file.
 *
 */
public final class SQLFileManager {

    private static final Logger LOG = LoggerFactory.getLogger(SQLFileManager.class);

    private static final int CACHE_SIZE = 5 * 1024 * 1024 / 4;

    /* Cache in Memory */
    private static final LruCache<String, String> cache = new LruCache<String, String>(CACHE_SIZE) {

        /**
         * {@inheritDoc}
         */
        @Override
        protected int sizeOf(String key, String value) {
            return value.length();
        }

    };

    /**
     * Read a sql from an outside file in src/main/resources.
     *
     * @param fileName	SQLFile Name. "sql/xxxx.sql"
     * @return	SQL
     */
    public static String readSql (String fileName) {

        // Create a cache key
        String key = createKey(fileName);

        String sql = cache.get(key);

        // If exists a sql in cache, Returns it.
        if (sql != null) {
            return sql;
        }

        try (
                InputStream in = SQLFileManager.class.getClassLoader().getResourceAsStream(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"))
                ) {

            StringBuilder response = new StringBuilder();

            int len;
            char[] buff = new char[4096];
            while ((len = br.read(buff, 0, 4096)) > -1) {
                response.append(buff, 0, len);
            }

            sql = response.toString();

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        if (sql != null) {

            sql = sql.replaceAll("\r\n", " ").replaceAll("\n", " ").replaceAll("\r", " ");

            cache.put(key, sql);

        } else {

            LOG.error("SQL-file not found.[" + fileName + "]");

        }

        return sql;

    }

    /**
     * Create Cache key.
     *
     */
    private static String createKey (String fileName) {

        StringBuilder sb = new StringBuilder();
        sb.append("sql#");
        sb.append(fileName);

        return sb.toString();

    }

    /**
     * private constructor.
     */
    private SQLFileManager() {
        // Nop
    }

}
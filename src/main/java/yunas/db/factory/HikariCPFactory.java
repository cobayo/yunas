package yunas.db.factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.configuration.Configuration;
import yunas.db.DBName;
import yunas.util.BaseUtil;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HikariCP Factory.
 *
 * Init HikariCP and Return DataSource.
 */
public class HikariCPFactory implements ConnectionPoolFactory {

    private static final Logger LOG = LoggerFactory.getLogger(HikariCPFactory.class);

    // Create DB Connection Pool with application.properties
    @Override
    public ConcurrentHashMap<String, DataSource> create(Configuration conf) {

        ConcurrentHashMap<String, DataSource> map = new ConcurrentHashMap<>();

        for (DBName dbName : DBName.values()) {

            if (BaseUtil.blank(conf.getDriverClassName(dbName)) && BaseUtil.blank(conf.getJdbcUrl(dbName))) {
                if (dbName == DBName.MASTER) {
                    LOG.warn("No using Master Databases");
                }
                return map;
            }

            // primary(master) or slave : Set config.
            HikariConfig config = bindConfig(conf, dbName);
            // Create Connection Pool
            map.put(dbName.getValue(), new HikariDataSource(config));

        }

        return map;

    }

    // Set config from application.properties.
    private HikariConfig  bindConfig(Configuration conf, DBName dbName) {

        HikariConfig config = new HikariConfig();

        // DriverName. for example "com.mysql.jdbc.Driver"
        config.setDriverClassName(conf.getDriverClassName(dbName));

        // jdbc url "jdbc:mysql://localhost:3306/your_db"
        config.setJdbcUrl(conf.getJdbcUrl(dbName));

        // User,password your yunas.db.
        config.setUsername(conf.getUser(dbName));
        config.setPassword(conf.getPassword(dbName));

        // Pool Size
        config.setMaximumPoolSize(conf.getMaxPoolSize(dbName));

        // ConnectionTimeout
        config.setConnectionTimeout(conf.getConnectionTimeout(dbName));

        // Prepared Statement: Always True.
        config.addDataSourceProperty("useServerPrepStmts", "true");

        // Query for Testing Connection.
        config.setConnectionInitSql("SELECT 1");

        return config;

    }

}

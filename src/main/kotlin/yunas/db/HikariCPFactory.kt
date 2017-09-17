package yunas.db

import yunas.configuration.Configuration
import yunas.util.BaseUtil
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory

import javax.sql.DataSource
import java.util.concurrent.ConcurrentHashMap

/**
 * HikariCP Factory.
 *
 * Init HikariCP and Return DataSource.
 */
class HikariCPFactory : ConnectionPoolFactory {

    // Create DB Connection Pool with application.properties
    override fun create(conf: Configuration): ConcurrentHashMap<String, DataSource> {

        val map = ConcurrentHashMap<String, DataSource>()

        for (dbName in DBName.values()) {

            if (BaseUtil.blank(conf.getDriverClassName(dbName)) && BaseUtil.blank(conf.getJdbcUrl(dbName))) {
                if (dbName == DBName.MASTER) {
                    LOG.warn("No using Master Databases")
                }
                return map
            }

            // primary(master) or slave : Set config.
            val config = bindConfig(conf, dbName)
            // Create Connection Pool
            map.put(dbName.value, HikariDataSource(config))

        }

        return map

    }

    // Set config from application.properties.
    private fun bindConfig(conf: Configuration, dbName: DBName): HikariConfig {

        val config = HikariConfig()

        // DriverName. for example "com.mysql.jdbc.Driver"
        config.driverClassName = conf.getDriverClassName(dbName)

        // jdbc url "jdbc:mysql://localhost:3306/your_db"
        config.jdbcUrl = conf.getJdbcUrl(dbName)

        // User,password your yunas.db.
        config.username = conf.getUser(dbName)
        config.password = conf.getPassword(dbName)

        // Pool Size
        config.maximumPoolSize = conf.getMaxPoolSize(dbName)

        // ConnectionTimeout
        config.connectionTimeout = conf.getConnectionTimeout(dbName)

        // Prepared Statement: Always True.
        config.addDataSourceProperty("useServerPrepStmts", "true")

        // Query for Testing Connection.
        config.connectionInitSql = "SELECT 1"

        return config

    }

    companion object {

        // For Logging
        private val LOG = LoggerFactory.getLogger(HikariCPFactory::class.java)
    }
}

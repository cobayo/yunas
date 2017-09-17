package yunas.db

import yunas.configuration.Configuration

import javax.sql.DataSource
import java.util.concurrent.ConcurrentHashMap

/**
 * Create ConnectionPool with HicariCP.
 *
 */
interface ConnectionPoolFactory {

    fun create(conf: Configuration): ConcurrentHashMap<String, DataSource>

}

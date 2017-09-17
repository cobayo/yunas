package org.yunas.db

import org.yunas.configuration.Configuration
import org.yunas.exception.YunasExceptionProvider
import org.yunas.util.BaseUtil

/**
 * DefaultDataSourceStrategy.
 *
 * Choose a connection pool.
 *
 */
class DefaultDataSourceStrategy : DataSourceStrategy {

    override fun getFactory(conf: Configuration): ConnectionPoolFactory {

        if (BaseUtil.blank(conf.get("yunas.cp")) || BaseUtil.equalsIgnoreCase(conf.get("yunas.cp"), "Hicaricp")) {
            return HikariCPFactory()
        }

        // Add other CP
        throw YunasExceptionProvider().unknownConnectionPoolName()
    }
}

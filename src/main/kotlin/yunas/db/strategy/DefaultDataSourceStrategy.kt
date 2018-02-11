package yunas.db.strategy

import yunas.configuration.Configuration
import yunas.db.factory.ConnectionPoolFactory
import yunas.db.factory.HikariCPFactory
import yunas.db.strategy.DataSourceStrategy
import yunas.exception.YunasExceptionProvider
import yunas.util.BaseUtil

/**
 * DefaultDataSourceStrategy.
 *
 * Choose a connection pool.
 *
 */
class DefaultDataSourceStrategy : DataSourceStrategy {

    override fun getFactory(conf: Configuration): ConnectionPoolFactory {

        if (BaseUtil.blank(conf.get("yunas.cp")) || BaseUtil.equalsIgnorecase(conf.get("yunas.cp"), "Hicaricp")) {
            return HikariCPFactory()
        }

        // Add other CP
        throw YunasExceptionProvider().unknownConnectionPoolName()
    }
}

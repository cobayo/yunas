package yunas.db.strategy

import yunas.configuration.Configuration
import yunas.db.factory.ConnectionPoolFactory

/**
 * DataSourceStrategy.
 *
 * @author yosuke kobayashi
 */
interface DataSourceStrategy {

    fun getFactory(conf: Configuration): ConnectionPoolFactory
}

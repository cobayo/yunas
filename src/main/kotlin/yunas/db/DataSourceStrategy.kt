package yunas.db

import yunas.configuration.Configuration

/**
 * DataSourceStrategy.
 *
 * @author yosuke kobayashi
 */
interface DataSourceStrategy {

    fun getFactory(conf: Configuration): ConnectionPoolFactory
}

package org.yunas.db

import org.yunas.configuration.Configuration

/**
 * DataSourceStrategy.
 *
 * @author yosuke kobayashi
 */
interface DataSourceStrategy {

    fun getFactory(conf: Configuration): ConnectionPoolFactory
}

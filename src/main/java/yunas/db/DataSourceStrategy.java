package yunas.db;

import yunas.configuration.Configuration;

/**
 * DataSourceStrategy.
 *
 * @author yosuke kobayashi
 */
public interface DataSourceStrategy {

    public ConnectionPoolFactory getFactory(Configuration conf);
}

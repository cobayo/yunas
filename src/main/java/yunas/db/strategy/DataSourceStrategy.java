package yunas.db.strategy;

import yunas.configuration.Configuration;
import yunas.db.factory.ConnectionPoolFactory;

/**
 * DataSourceStrategy.
 *
 * @author yosuke kobayashi
 */
public interface DataSourceStrategy {

    public ConnectionPoolFactory getFactory(Configuration conf);
}

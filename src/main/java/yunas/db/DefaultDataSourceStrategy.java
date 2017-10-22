package yunas.db;

import yunas.configuration.Configuration;
import yunas.exception.YunasExceptionProvider;
import yunas.util.BaseUtil;

/**
 * DefaultDataSourceStrategy.
 *
 * Choose a connection pool.
 *
 */
public class DefaultDataSourceStrategy implements DataSourceStrategy {

     @Override
     public ConnectionPoolFactory  getFactory(Configuration conf) {

        if (BaseUtil.blank(conf.get("yunas.cp")) || BaseUtil.equalsIgnorecase(conf.get("yunas.cp"), "Hicaricp")) {
            return new HikariCPFactory();
        }

        // Add other CP
        throw new YunasExceptionProvider().unknownConnectionPoolName();
    }
}

package yunas.db.factory;

import yunas.configuration.Configuration;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create ConnectionPool with HicariCP.
 *
 */
public interface ConnectionPoolFactory {

    public ConcurrentHashMap<String, DataSource> create(Configuration conf);

}

package yunas.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.configuration.Configuration;
import yunas.db.factory.ConnectionPoolFactory;
import yunas.db.strategy.DataSourceStrategy;
import yunas.exception.YunasExceptionProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Store DataSources.
 *
 * @author yosuke kobayashi
 */
public class Databases {

    private static Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    private static final Logger LOG = LoggerFactory.getLogger(Databases.class);

    public synchronized static void init(Configuration conf, DataSourceStrategy sourceStrategy) {

        if (!dataSourceMap.isEmpty()) {
            LOG.warn("Already Init");
            return;
        }
        ConnectionPoolFactory factory = sourceStrategy.getFactory(conf);
        dataSourceMap = factory.create(conf);

    }

    public static DataSource getDataSource(String dbName) {

        if (dataSourceMap.containsKey(dbName)) {
            return dataSourceMap.get(dbName);
        }

        throw new YunasExceptionProvider().unknownDBName(dbName);
    }

    public static Connection getConnection() throws SQLException {
        return getConnection(DBName.MASTER.getValue(),true);

    }

    public static Connection getConnection(DBName dbName) throws SQLException {
        return getConnection(dbName.getValue(),true);

    }

    public static Connection getConnection(String dbName, boolean autoCommit) throws SQLException {

        DataSource dataSource = getDataSource(dbName);
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }

}

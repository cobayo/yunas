package yunas.configuration;

import yunas.db.DBName;
import yunas.util.BaseUtil;

import java.util.Properties;

/**
 * Settings For YunasApp Server.
 *
 * @author Yosuke Kobayashi
 */
public class Configuration {

    private static final int DEFAULT_PORT = 10421;

    private static final int DEFAULT_MAX_DB_POOL_SIZE = 16;

    private static final int DEFAULT_MAX_THREADS = 200;

    private static final int DEFAULT_MIN_THREADS = 20;

    private Properties properties = new Properties();

    // Package private Constructor
    Configuration() {

    }

    // Must Package Private
    void setProperties(Properties properties) {
        this.properties = properties;
    }
    /** Getters  */

    public int getPort() {
        if (properties.getProperty("yunas.port") == null) {
            return DEFAULT_PORT;
        } else {
            return BaseUtil.parseInt(properties.getProperty("yunas.port"));
        }
    }

    public String getEnv() {
        return properties.getProperty("yunas.env");
    }

    public String getSecret() {
        return properties.getProperty("yunas.secret");
    }

    public String getSessionKey() {
        return properties.getProperty("yunas.session.key","YUNAS_SESSION");
    }

    public int getSessionMaxAge() {
        return BaseUtil.parseInt(properties.getProperty("yunas.session.maxAge","86400"));
    }

    public String get(String key) {
        return properties.getProperty(key, "");
    }

     public String getDriverClassName(DBName dbName) {

        String key = (dbName == DBName.MASTER) ? "yunas.db.driverClassName" : "yunas.db.secondary.driverClassName";
        return properties.getProperty(key, "");
    }

    public String getJdbcUrl(DBName dbName){
        String key = (dbName == DBName.MASTER) ? "yunas.db.jdbcUrl" : "yunas.db.secondary.jdbcUrl";
        return properties.getProperty(key, "");
    }

    public String getUser(DBName dbName){
        String  key = (dbName == DBName.MASTER) ? "yunas.db.user" : "yunas.db.secondary.user";
        return properties.getProperty(key, "");
    }

    public String getPassword(DBName dbName) {
        String  key = (dbName == DBName.MASTER) ? "yunas.db.password" : "yunas.db.secondary.password";
        return properties.getProperty(key, "");
    }

    public int getMaxPoolSize(DBName dbName) {
        String key = (dbName == DBName.MASTER)? "yunas.db.maxPoolSize" : "yunas.db.secondary.maxPoolSize";
        return BaseUtil.parseInt(properties.getProperty(key, String.valueOf(DEFAULT_MAX_DB_POOL_SIZE)));
    }

    public Long getConnectionTimeout(DBName dbName){
        String key =  (dbName == DBName.MASTER) ? "yunas.db.connectionTimeout" : "yunas.db.secondary.connectionTimeout";
        return BaseUtil.parseLong(properties.getProperty(key, ""));
    }

    public int getMaxThreads() {
        return BaseUtil.parseInt(properties.getProperty("yunas.maxThreads", String.valueOf(DEFAULT_MAX_THREADS)));
    }

    public int getMinThreads() {
        return BaseUtil.parseInt(properties.getProperty("yunas.minThreads", String.valueOf(DEFAULT_MIN_THREADS)));
    }

    public int getMaxFormContentSize() {
        return BaseUtil.parseInt(properties.getProperty("yunas.maxFormContentSize", "1073741824"));
    }

}

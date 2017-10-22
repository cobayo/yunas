package yunas.configuration

import yunas.db.DBName
import yunas.util.BaseUtil

import java.util.Properties

/**
 * Settings For YunasApp Server.
 *
 * @author Yosuke Kobayashi
 */
class Configuration// Package private Constructor
internal constructor() {

    private var properties = Properties()

    // Must Package Private
    internal fun setProperties(properties: Properties) {
        this.properties = properties
    }

    /** Getters  */

    val port: Int
        get() = if (properties.getProperty("yunas.port") == null) DEFAULT_PORT else BaseUtil.parseInt(properties.getProperty("yunas.port"))

    val env: String?
        get() = properties.getProperty("yunas.env")
    val secret: String?
        get() = properties.getProperty("yunas.secret")
    val sessionKey: String
        get() = properties.getProperty("yunas.session.key","YUNAS_SESSION")
    val sessionMaxAge: Int
        get() = BaseUtil.parseInt(properties.getProperty("yunas.session.maxAge","86400"))

    operator fun get(key: String): String {

        return properties.getProperty(key, "")
    }

    fun getDriverClassName(dbName: DBName): String {

        val key = if (dbName === DBName.MASTER) "yunas.db.driverClassName" else "yunas.db.secondary.driverClassName"
        return properties.getProperty(key, "")
    }

    fun getJdbcUrl(dbName: DBName): String {
        val key = if (dbName === DBName.MASTER) "yunas.db.jdbcUrl" else "yunas.db.secondary.jdbcUrl"
        return properties.getProperty(key, "")
    }

    fun getUser(dbName: DBName): String {
        val key = if (dbName === DBName.MASTER) "yunas.db.user" else "yunas.db.secondary.user"
        return properties.getProperty(key, "")
    }

    fun getPassword(dbName: DBName): String {
        val key = if (dbName === DBName.MASTER) "yunas.db.password" else "yunas.db.secondary.password"
        return properties.getProperty(key, "")
    }

    fun getMaxPoolSize(dbName: DBName): Int {
        val key = if (dbName === DBName.MASTER) "yunas.db.maxPoolSize" else "yunas.db.secondary.maxPoolSize"
        return BaseUtil.parseInt(properties.getProperty(key, DEFAULT_MAX_DB_POOL_SIZE.toString()))
    }

    fun getConnectionTimeout(dbName: DBName): Long {
        val key = if (dbName === DBName.MASTER) "yunas.db.connectionTimeout" else "yunas.db.secondary.connectionTimeout"
        return BaseUtil.parseLong(properties.getProperty(key, ""))
    }

    val maxThreads: Int
        get() = BaseUtil.parseInt(properties.getProperty("yunas.maxThreads", DEFAULT_MAX_THREADS.toString()))


    val minThreads: Int
        get() = BaseUtil.parseInt(properties.getProperty("yunas.minThreads", DEFAULT_MIN_THREADS.toString()))

    val maxFormContentSize: Int
        get() = BaseUtil.parseInt(properties.getProperty("yunas.maxFormContentSize", "1073741824"))

    companion object {

        private val DEFAULT_PORT = 10421

        private val DEFAULT_MAX_DB_POOL_SIZE = 16

        private val DEFAULT_MAX_THREADS = 200

        private val DEFAULT_MIN_THREADS = 20
    }


}

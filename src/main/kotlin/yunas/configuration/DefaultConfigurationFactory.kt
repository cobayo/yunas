package yunas.configuration

import org.slf4j.LoggerFactory
import yunas.exception.YunasExceptionProvider
import yunas.util.BaseUtil
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 * Configuration from src/main/resources/application.properties .
 *
 * @author Yosuke Kobayashi
 */
class DefaultConfigurationFactory : ConfigurationFactory {

    override fun create(): Configuration {

        // System properties
        val systemProperties = System.getProperties()

        // Config Properties For YunasApp
        val conf = getPropertyFilePath(systemProperties)

        // port
        if (systemProperties["yunas.port"] != null) {
            conf.put("yunas.port", systemProperties["yuas.port"].toString())
        }

        val settingInfo = Configuration()
        settingInfo.setProperties(conf)

        return settingInfo
    }

    // Find application.properties
    private fun getPropertyFilePath(systemProperties: Properties): Properties {

        // Result
        val conf = Properties()

        // Find env fileName. like -Dyunas.profiles.active=local. application-<env fileName>.properties. DEFAULT(PRODUCTION) is application.properties
        val envFromSystem = System.getenv("YUNAS_PROFILES_ACTIVE")
        val envFromProperty = systemProperties["yunas.profiles.active"]
        val envName = envFromProperty?.toString() ?: envFromSystem


        val propertyFileName = if (BaseUtil.blank(envName) || "production" === envFromProperty) ConfFileName.DEFAULT.fileName else "application-\$envName.properties"

        // Loading from resources
        val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(propertyFileName)


        if (inputStream == null) {

            if (BaseUtil.blank(envName)) {
                LOG.warn("not found application.properties")
                return conf
            }

            // Set Env But Not Found
            throw YunasExceptionProvider().failToGetApplicationProperties()
        }

        try {
            BufferedReader(InputStreamReader(inputStream)).use { br ->


                conf.load(br)

            }
        } catch (e: IOException) {
            throw YunasExceptionProvider().failToGetApplicationProperties(e)
        }

        return conf
    }

    companion object {


        private val LOG = LoggerFactory.getLogger(DefaultConfigurationFactory::class.java)
    }

}

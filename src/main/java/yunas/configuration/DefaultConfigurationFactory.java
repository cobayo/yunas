package yunas.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.exception.YunasExceptionProvider;
import yunas.util.BaseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Configuration from src/main/resources/application.properties .
 *
 * @author Yosuke Kobayashi
 */
public class DefaultConfigurationFactory implements ConfigurationFactory {


    private static final Logger LOG = LoggerFactory.getLogger(DefaultConfigurationFactory.class);

    @Override
    public Configuration create(){

        // System properties
        Properties systemProperties = System.getProperties();

        // Config Properties For YunasApp
        Properties conf = getPropertyFilePath(systemProperties);

        // port
        if (systemProperties.get("yunas.port") != null) {
            conf.put("yunas.port", systemProperties.get("yuas.port").toString());
        }

        Configuration settingInfo = new Configuration();
        settingInfo.setProperties(conf);

        return settingInfo;
    }

    // Find application.properties
    private Properties getPropertyFilePath(Properties systemProperties) {

        // Result
        Properties conf = new Properties();

        // Find env fileName. like -Dyunas.profiles.active=local. application-<env fileName>.properties. DEFAULT(PRODUCTION) is application.properties
        String envFromSystem = System.getenv("YUNAS_PROFILES_ACTIVE");
        Object envFromProperty = systemProperties.get("yunas.profiles.active");
        String envName = envFromProperty != null ? envFromProperty.toString() : envFromSystem;


        String propertyFileName = (BaseUtil.blank(envName) || "production" == envFromProperty) ? ConfFileName.DEFAULT.getFileName() : "application-$envName.properties";

        // Loading from resources
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFileName);


        if (inputStream == null) {

            if (BaseUtil.blank(envName)) {
                LOG.warn("not found application.properties");
                return conf;
            }

            // Set Env But Not Found
            throw new YunasExceptionProvider().failToGetApplicationProperties();
        }

        try (
                BufferedReader br =   new BufferedReader(new InputStreamReader(inputStream))
                ){


                conf.load(br);

        } catch (IOException e) {
            throw new YunasExceptionProvider().failToGetApplicationProperties(e.getCause());
        }

        return conf;
    }

}

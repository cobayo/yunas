package yunas.configuration

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

/**
 * ConfigurationFactoryTest.
 *
 * JUnit.
 */

class ConfigurationFactoryTest {

    @Test fun createTest() {

        val settingInfo = DefaultConfigurationFactory().create();
        Assert.assertThat(settingInfo.port, CoreMatchers.`is`(10421))
        println("port :" + settingInfo.port)

    }


}
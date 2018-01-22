package yunas.db

import yunas.configuration.DefaultConfigurationFactory
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import yunas.Yunas
import yunas.db.factory.HikariCPFactory
import javax.sql.DataSource

/**
 *
 * HikariCPFactoryTest.
 *
 */
class HikariCPFactoryTest {


    @Ignore @Test fun createTest() {

        val conf = DefaultConfigurationFactory().create()
        val dataSources = HikariCPFactory().create(conf)

        val dataSource : DataSource? = dataSources[DBName.MASTER.value]
        Assert.assertNotNull(dataSource)

        Yunas.kill()

    }


}
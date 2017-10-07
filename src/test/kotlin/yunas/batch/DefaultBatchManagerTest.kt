package yunas.batch

import org.junit.Assert
import org.junit.Test

/**
 * DefaultBatchManagerTest.
 *
 */
class DefaultBatchManagerTest {

    @Test fun runTest() {

        val p = System.getProperties()
        p.setProperty("yunas.batch","test")
        System.setProperties(p)

        val manager = DefaultBatchManager()
        var output: String? = null

        manager.add("test",{(args) ->
            output = args[0].toString()
        })

        val args = arrayOf("1","2")
        manager.run(args)

        Assert.assertTrue(output == "1")

    }
}
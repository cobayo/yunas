package yunas.batch

import org.junit.Assert
import org.junit.Test
import yunas.Batch

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

        val batch = TestBatch()
        manager.add("test",batch)

        val args = arrayOf("1","2")
        manager.run(args)

        Assert.assertTrue( batch.getOutput() == "1")

    }

    private class TestBatch : Batch {

        private var output: String? = null

        override fun action(args: Array<String>) {
            output = args[0]
        }

       fun getOutput() : String? {
           return output
       }
    }
}


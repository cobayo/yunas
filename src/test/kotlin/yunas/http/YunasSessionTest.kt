package yunas.http

import org.junit.Assert
import org.junit.Test

/**
 * YunasSessionTest.
 *
 */
class YunasSessionTest {

    @Test fun YunasSessionTest() {


        val session : YunasSession = YunasSession(mutableMapOf())
        session.add("Yunas","Kotlin")

        Assert.assertTrue(session.get("Yunas") == "Kotlin")


    }
}
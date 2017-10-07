package yunas.util

import org.junit.Assert
import org.junit.Test

/**
 * CookieSignerTest.
 *
 */
class CookieSignerTest {


    @Test fun cookieSingerTest() {

        val message = "Yunas=Kotlin"
        val expect = "c54fbceb8240ec992fde0f9d53a2de41c48b752a" //secret = pleasechangeme123456

        val signed =  CookieSigner.sign(message)
        Assert.assertTrue(expect == signed)

    }


}
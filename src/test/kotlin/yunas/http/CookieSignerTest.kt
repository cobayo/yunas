package yunas.http

import org.junit.Assert
import org.junit.Test
import yunas.http.CookieSigner

/**
 * CookieSignerTest.
 *
 */
class CookieSignerTest {


    @Test fun cookieSingerTest() {

        val message = "Yunas=Kotlin"
        val expect = "7bfec1a02d1c6c5a6ecaf7da2990c07ddbe00f12ca" //secret = pleasechangeme123456
        val signed =  CookieSigner.sign(message)

        Assert.assertTrue(expect == signed)

    }


}
package yunas.util

import org.junit.Assert
import org.junit.Test
import yunas.Yunas

/**
 * YunasSessionCookieBakerTest.
 */
class YunasSessionCookieBakerTest {

    val data = mapOf("Yunas" to "Kotlin","Server" to "Jetty")

    @Test
    fun encodeAndDecodeTest() {

        // Success
        val encode = YunasSessionCookieBaker.encode(data)
        val data = YunasSessionCookieBaker.decode(encode)

        Assert.assertTrue("Kotlin".equals(data.get("Yunas")))

        // Fail
        val defacingEncode = encode + "&attack=1"

        val defacingData = YunasSessionCookieBaker.decode(defacingEncode)

        Assert.assertFalse("Kotlin".equals(defacingData.get("Yunas")))

        Yunas.kill()

    }
}
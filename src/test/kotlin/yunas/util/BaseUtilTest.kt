import yunas.util.BaseUtil
import org.junit.Test
import org.junit.Assert.assertThat
import org.hamcrest.CoreMatchers.`is`

/**
 * BaseUtilTest.
 *
 * Unit Test in Kotlin.
 */
class BaseUtilTest {

    @Test fun testParseInt() {

        val actual = BaseUtil.parseInt("123")
        assertThat(actual, `is`(123))
    }

    @Test fun testParseLong() {

        val actual = BaseUtil.parseLong("4231212121212121211")
        assertThat(actual, `is`(4231212121212121211))
    }
}
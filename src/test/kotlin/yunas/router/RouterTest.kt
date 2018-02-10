package yunas.router

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import yunas.Context
import yunas.Controller
import yunas.http.DefaultContentType
import yunas.http.HttpMethod

/**
 * Router Test.
 */


class RouterTest {


    @Test fun findTest() {

        val router = Router()

        router.add(HttpMethod.POST,"/test",TestController(),DefaultContentType.HTML)

        val testFunc : Route = router.find(HttpMethod.POST,"/test")

        val context: Context = Mockito.mock(Context::class.java)
        Assert.assertTrue(testFunc.getController().apply(context) == "world")


    }

    private class TestController :Controller {

        override fun action(context: Context): Any {
            return "world"
        }
    }



}
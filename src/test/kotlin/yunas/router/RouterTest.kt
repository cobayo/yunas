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

class TestController : Controller {
    override fun invoke(p1: Context): Any {
        return "Hello"
    }
}

val test  = fun (context: Context?) : String {
    return "world"
}

class RouterTest {


    @Test fun findTest() {

        val router = Router()

        router.add(HttpMethod.GET,"/",TestController(),DefaultContentType.HTML)
        router.add(HttpMethod.POST,"/test",test,DefaultContentType.HTML)

        val helloFunc = router.find(HttpMethod.GET,"/")
        val testFunc : Route = router.find(HttpMethod.POST,"/test")

        val context: Context = Mockito.mock(Context::class.java)
        Assert.assertTrue(helloFunc.getController().invoke(context) == "Hello")
        Assert.assertTrue(testFunc.getController().invoke(context) == "world")


    }



}
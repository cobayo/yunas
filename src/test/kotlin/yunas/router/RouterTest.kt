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


val test  = fun (context: Context?) : String {
    return "world"
}

class RouterTest {


    @Test fun findTest() {

        val router = Router()

        router.add(HttpMethod.POST,"/test",test,DefaultContentType.HTML)

        val testFunc : Route = router.find(HttpMethod.POST,"/test")

        val context: Context = Mockito.mock(Context::class.java)
   //     Assert.assertTrue(testFunc.getController().apply(context) == "world")


    }



}
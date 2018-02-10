package yunas.handler

import org.junit.Assert
import org.junit.Test
import yunas.Context
import yunas.Controller
import yunas.Yunas
import yunas.http.DefaultContentType
import yunas.http.HttpMethod


class ExecuteTest {


    /**
     * Todo Use Mockito. Add Comment.
     */
    @Test fun executeTest() {

        val req = MockHttpServletRequest()
        val res = MockHttpServletResponse()

        Yunas.Rest.get("/execute_test", TestController())
        Yunas.get("/execute_test_html", TestController())

        req.requestURI = "/execute_test"
        Execute.execute(req,res,HttpMethod.GET)
        Assert.assertTrue(DefaultContentType.JSON.value == res.contentType)


        req.requestURI = "/execute_test_html"
        Execute.execute(req,res,HttpMethod.GET)
        Assert.assertTrue(DefaultContentType.HTML.value == res.contentType)
    }

    private class TestController : Controller {

        override fun action(context: Context): Any {
            return "Hello"
        }
    }


}
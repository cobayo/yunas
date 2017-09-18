package yunas.handler

import org.junit.Assert
import org.junit.Test
import yunas.Context
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

        Yunas.Rest["/execute_test", { context : Context -> "Hello" }]
        Yunas["/execute_test_html", { context : Context -> "Hello" }]

        req.requestURI = "/execute_test"
        Execute.execute(req,res,HttpMethod.GET)
        Assert.assertTrue(DefaultContentType.JSON.value == res.contentType)


        req.requestURI = "/execute_test_html"
        Execute.execute(req,res,HttpMethod.GET)
        Assert.assertTrue(DefaultContentType.HTML.value == res.contentType)
    }


}
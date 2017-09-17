package yunas.handler

import org.junit.Assert
import org.junit.Test
import yunas.Context
import yunas.Yunas
import yunas.http.DefaultContentType
import yunas.http.HttpMethod


class ExecuteTest {


    @Test fun executeTest() {

        val req = MockHttpServletRequest()
        val res = MockHttpServletResponse()

        Yunas.Rest["/execute_test", { context : Context -> "Hello" }]

        req.requestURI = "/execute_test"

        Execute.execute(req,res,HttpMethod.GET)

        Assert.assertTrue(DefaultContentType.JSON.value == res.contentType)

    }


}
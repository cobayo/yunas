package org.yunas.handler

import org.junit.Assert
import org.junit.Test
import org.yunas.Context
import org.yunas.Yunas
import org.yunas.http.DefaultContentType
import org.yunas.http.HttpMethod


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
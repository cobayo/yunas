package yunas.filter

import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*
import javax.servlet.FilterChain
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * StaticFilterTest.
 */
class StaticFilterTest {


    @Test fun doFilterTest() {

        val req = mock(HttpServletRequest::class.java)
        val res = mock(HttpServletResponse::class.java)
        val chain = mock(FilterChain::class.java)
        val rd = mock(RequestDispatcher::class.java)
        val filter = StaticFilter()


        `when`(req.getRequestURI()).thenReturn("/test.css")
        `when`(req.getRequestDispatcher(Matchers.anyString())).thenReturn(rd)
         filter.doFilter(req,res,chain)

         verify(chain,times(0)).doFilter(req,res)
         verify(rd, times(1)).forward(req,res)

    }
}
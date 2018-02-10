package yunas.http

import org.slf4j.LoggerFactory
import yunas.Yunas
import yunas.exception.YunasException
import yunas.exception.YunasExceptionProvider
import yunas.util.BaseUtil
import yunas.util.HashUtil

/**
 * Signed messages for baking signed cookies.
 *
 */
object CookieSigner {

    private val LOG = LoggerFactory.getLogger(CookieSigner::class.java)

    fun sign(message: String): String {

        try {

            val secret = Yunas.configuration!!.secret

            if (BaseUtil.blank(secret)) {
                throw YunasExceptionProvider().unSetSecret()
            }

            return HashUtil.sha256(message + secret).substring(0, 42)

        } catch (e: YunasException) {
            LOG.warn(e.message)
            throw e
        }

    }

}
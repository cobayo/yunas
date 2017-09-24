package yunas.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import yunas.Yunas
import yunas.exception.YunasException
import yunas.exception.YunasExceptionProvider

/**
 * Signed messages for baking signed cookies.
 *
 */
object CookieSigner {

    private val LOG = LoggerFactory.getLogger(CookieSigner::class.java)

    fun sign(message: String): String {

        try {

            val secret = Yunas.configuration?.secret
            if (BaseUtil.blank(secret)) {
                throw YunasExceptionProvider().unSetSecret()
            }

            return HashUtil.sha1(message + secret)

        } catch (e: YunasException) {
            LOG.warn(e.message)
            throw e;
        }

    }

}

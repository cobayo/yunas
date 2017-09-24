package yunas.util

import org.slf4j.LoggerFactory
import yunas.exception.YunasExceptionProvider
import java.net.URLEncoder

/**
 * Create Signed Cookie.
 */
object YunasSessionCookieBaker {

    // Logger
    private val LOG = LoggerFactory.getLogger(YunasSessionCookieBaker::class.java)

    fun encode(data :Map<String,String>) : String {

        if (data.isEmpty()) {
            return ""
        }

        val encode = data.map { URLEncoder.encode(it.key,"UTF-8") + "=" + URLEncoder.encode(it.value,"UTF-8") }.joinToString("&")

        return CookieSigner.sign(encode) + "-" + encode
    }

    fun decode(value : String?) : Map<String,String> {

        val split = value?.split("-")
        if (split == null || split.size <= 1) {
            return mapOf()
        }

        try {

            val message = split.get(0)
            val data = split.get(1)
            if (!BaseUtil.equals(message,CookieSigner.sign(data))) {
                throw YunasExceptionProvider().notMatchSign(value)
            }

            return data.split("&").map {
                val v = it.split("=")
                if (v.size <= 1) {
                    "" to ""
                } else {
                    v.get(0) to v.get(1)
                }
            }.toMap()


        } catch (e : Exception) {
            LOG.info(e.message)
            return mapOf()
        }
    }


}

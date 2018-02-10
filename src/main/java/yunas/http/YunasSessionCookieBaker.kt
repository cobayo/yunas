package yunas.http

import org.slf4j.LoggerFactory
import yunas.exception.YunasExceptionProvider
import yunas.util.BaseUtil
import java.net.URLEncoder
import java.util.*
import java.util.stream.Collectors

/**
 * Create Signed Cookie.
 */
object YunasSessionCookieBaker {

    // Logger
    private val LOG = LoggerFactory.getLogger(YunasSessionCookieBaker::class.java)

    fun encode(data: Map<String, String>): String {

        if (data.isEmpty()) {
            return ""
        }

        return try {

            val encode = data.entries.stream().map { it -> encodeUrl(it.key) + "=" + encodeUrl(it.value) }.collect(Collectors.joining("&"))

            CookieSigner.sign(encode) + "-" + encode


        } catch (e: Exception) {
            LOG.info(e.message)
            ""
        }

    }

    private fun encodeUrl(value: String): String {

        try {

            return URLEncoder.encode(value, "UTF-8")

        } catch (e: Exception) {
            LOG.error(e.message)
            return ""
        }

    }

    fun decode(value: String?): MutableMap<String, String> {

        if (value == null) {
            return mutableMapOf()
        }

        val split = value.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (split.size <= 1) {
            return mutableMapOf()
        }

        try {

            val message = split[0]
            val data = split[1]
            if (!BaseUtil.equals(message, CookieSigner.sign(data))) {
                throw YunasExceptionProvider().notMatchSign(value)
            }

            val res = HashMap<String, String>()
            Arrays.stream(data.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()).forEach { it ->

                val v = it.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (v.size >= 1) {
                    res.put(v[0], v[1])
                }
            }

            return res


        } catch (e: Exception) {
            LOG.info(e.message)
            return mutableMapOf()
        }

    }


}

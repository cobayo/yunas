package yunas.http

import org.slf4j.LoggerFactory
import yunas.exception.YunasExceptionProvider
import yunas.util.BaseUtil
import yunas.util.CookieSigner
import java.net.URLEncoder

/**
 * Signed Cookie Session in Yunas.
 *
 * Session Key is yunas.session.key in application.properties.
 * Session Expire is yunas.session.expire in application.properties.(Default is 1 day)
 * Signer's secret key is yunas.secret in application.properties.
 */
class YunasSession(val data: MutableMap<String, String>) {


    fun get(key: String): String? {
        return if (data.containsKey(key)) data[key] else ""

    }

    fun add(key: String, value: String) {
        data.put(key, value)
    }

    fun del(key: String) {
        if (data.containsKey(key)) {
            data.remove(key)
        }
    }



}

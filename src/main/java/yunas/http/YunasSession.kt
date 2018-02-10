package yunas.http

/**
 * Signed Cookie Session in Yunas.
 *
 * Session Key is yunas.session.key in application.properties.
 * Session Expire is yunas.session.expire in application.properties.(Default is 1 day)
 * Signer's secret key is yunas.secret in application.properties.
 */
class YunasSession(data : MutableMap<String, String>) {

    private val data = mutableMapOf<String,String>()

    init {
        this.data.clear()
        this.data.putAll(data)
    }

    operator fun get(key: String): String {
        return data.getOrDefault(key, "")

    }

    fun add(key: String, value: String) {
        data.put(key, value)
    }

    fun del(key: String) {
        if (data.containsKey(key)) {
            data.remove(key)
        }
    }

    fun getData(): Map<String, String> {
        return data
    }
}

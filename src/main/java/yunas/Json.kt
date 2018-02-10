package yunas

import com.google.gson.Gson
import com.google.gson.JsonObject

/**
 * Wrap Third Json Lib. (Gson).
 *
 * @author yosuke kobayashi
 */
class Json<T> {

    fun toJson(obj: Any): String {
        return Gson().toJson(obj)
    }

    fun fromJson(jsonString: String, clazz: Class<T>): T {
        return Gson().fromJson(jsonString, clazz)
    }

    fun fromJson(jsonString: String): JsonObject {
        return Gson().fromJson(jsonString, JsonObject::class.java)
    }
}

package org.yunas

import com.google.gson.Gson
import com.google.gson.JsonObject

/**
 * Wrap Third Json Lib. (Gson).
 *
 * @author yosuke kobayashi
 */
object Json {

    fun toJson(`object`: Any): String {
        return Gson().toJson(`object`)
    }

    fun <T> fromJson(jsonString: String, clazz: Class<T>): T {
        return Gson().fromJson(jsonString, clazz)
    }

    fun fromJson(jsonString: String): JsonObject {
        return Gson().fromJson(jsonString, JsonObject::class.java)
    }
}

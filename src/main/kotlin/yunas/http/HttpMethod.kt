package yunas.http

/**
 * HttpMethod.
 *
 */
enum class HttpMethod private constructor(private val value: String) {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    TRACE("TRACE"),
    OPTIONS("OPTIONS")
}

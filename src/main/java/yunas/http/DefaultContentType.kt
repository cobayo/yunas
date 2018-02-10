package yunas.http

/**
 * DefaultContentType.
 *
 * @author yosuke kobayashi
 */
enum class DefaultContentType private constructor(val value: String) {

    JSON("application/json"),
    XML("application/xml"),
    HTML("text/html"),
    PLAIN("text/plain")
}

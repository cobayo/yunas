package yunas.exception

/**
 * YunasApp Exception.
 *
 *
 * @author Yosuke Kobayashi
 */
class YunasException : RuntimeException {

    private var code: String? = null


    internal constructor(code: String, message: String) : super(message) {
        this.code = code
    }

    internal constructor(code: String, message: String, cause: Throwable) : super(message, cause) {
        this.code = code
    }
}

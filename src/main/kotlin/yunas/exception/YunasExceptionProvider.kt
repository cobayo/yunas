package yunas.exception

/**
 * ExceptionProvider.
 *
 * @author  Yosuke Kobayashi
 */
class YunasExceptionProvider {


    fun unsupportedReturnType(): YunasException {
        return YunasException("UNSUPPORTED_RETURN_TYPE", "Return Type from Controller is not supported")
    }


    fun failToGetApplicationProperties(cause: Throwable?): YunasException {
        return YunasException("FAIL_TO_GET_APPLICATION_PROPERTIES", "YunasApp can not get yunas.configuration", cause)
    }

    fun failToGetApplicationProperties(): YunasException {
        return YunasException("FAIL_TO_GET_APPLICATION_PROPERTIES", "YunasApp can not get yunas.configuration")
    }

    fun failToInit(cause: Throwable?): YunasException {
        return YunasException("FAIL_TO_INIT", "YunasApp can not init", cause)
    }

    fun unknownConnectionPoolName(): YunasException {
        return YunasException("UNKNOWN_CONNECTION_POOL_NAME", "GIVEN UNKNOWN Connection Pool Name in Properties")
    }

    fun unknownDBName(dbName: String): YunasException {
        return YunasException("UNKNOWN_DB_NAME", "GIVEN UNKNOWN Databases Name:" + dbName)
    }


    fun notFoundPath(path: String): YunasNotFoundException {
        return YunasNotFoundException("NOT_FOUND_PATH", "not found the path: " + path)
    }


}

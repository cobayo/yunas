package yunas.exception;

/**
 * ExceptionProvider.
 *
 * @author  Yosuke Kobayashi
 */
public class YunasExceptionProvider {


    public YunasException unsupportedReturnType() {
        return new YunasException("UNSUPPORTED_RETURN_TYPE", "Return Type from Controller is not supported");
    }


    public YunasException failToGetApplicationProperties(Throwable cause) {
        return new YunasException("FAIL_TO_GET_APPLICATION_PROPERTIES", "YunasApp can not get yunas.configuration", cause);
    }

    public YunasException failToGetApplicationProperties() {
        return new YunasException("FAIL_TO_GET_APPLICATION_PROPERTIES", "YunasApp can not get yunas.configuration");
    }

    public YunasException failToInit(Throwable cause){
        return new YunasException("FAIL_TO_INIT", "YunasApp can not init", cause);
    }

    public YunasException unknownConnectionPoolName() {
        return new YunasException("UNKNOWN_CONNECTION_POOL_NAME", "GIVEN UNKNOWN Connection Pool Name in Properties");
    }

    public YunasException  unknownDBName(String dbName){
        return new YunasException("UNKNOWN_DB_NAME", "GIVEN UNKNOWN Databases Name:" + dbName);
    }


    public YunasNotFoundException notFoundPath(String path) {
        return new YunasNotFoundException("NOT_FOUND_PATH", "not found the path: " + path);
    }

    public YunasException notMatchSign(String value) {
        return new YunasException("NOT_MATCH_SIGN", "not match sign : " + value);
    }

    public YunasException unSetSecret() {
        return new YunasException("UNSET_SECRET", "yunas.secret.required using YunasSession");
    }

    public YunasNotFoundException notFoundTemplateEngine() {
        return new YunasNotFoundException("NOT_FOUND_TEMPLATE_ENGINE", "Required TemplateEngine, When ContentType is HTML And Return Not String Value: ");
    }


}

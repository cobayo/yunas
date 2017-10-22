package yunas.http;

/**
 * HttpMethod.
 *
 */
public enum HttpMethod  {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    TRACE("TRACE"),
    OPTIONS("OPTIONS");

    private String value;

    HttpMethod(String value) {
        this.value = value;
    }
}

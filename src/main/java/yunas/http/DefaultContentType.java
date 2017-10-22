package yunas.http;

/**
 * DefaultContentType.
 *
 * @author yosuke kobayashi
 */
public enum DefaultContentType  {

    JSON("application/json"),
    XML("application/xml"),
    HTML("text/html"),
    PLAIN("text/plain");

    private String value;

    DefaultContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package yunas.exception;

/**
 * YunasNotFoundException.
 */
public class YunasNotFoundException extends RuntimeException {

    private String code;
    private String message;

    YunasNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}

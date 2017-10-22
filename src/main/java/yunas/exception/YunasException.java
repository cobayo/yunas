package yunas.exception;

/**
 * YunasApp Exception.
 *
 *
 * @author Yosuke Kobayashi
 */
public class YunasException extends RuntimeException {

    private String code = null;


    YunasException(String code, String message)  {
        super(message);
        this.code = code;
    }

    YunasException(String code, String message, Throwable cause)  {
        super(message, cause);
        this.code = code;
    }
}

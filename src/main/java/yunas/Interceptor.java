package yunas;

import java.util.function.Consumer;

/**
 * Interceptor interface for Java.
 */
public interface Interceptor extends Consumer<Context> {

    public void action(Context context);

    @Override
    default void accept(Context context) {
        action(context);
    }
}

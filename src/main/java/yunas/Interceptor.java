package yunas;

import kotlin.Unit;
import yunas.Context;

import java.util.function.Function;

/**
 * Interceptor interface for Java.
 */
public interface Interceptor extends Function<Context, Unit> {

    public void action(Context context);

    @Override
    default Unit apply(Context context) {
        action(context);
        return null;
    }
}

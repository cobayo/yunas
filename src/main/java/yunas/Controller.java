package yunas;

import yunas.Context;

import java.util.function.Function;

/**
 * yunas.Controller interface for java.
 */
public interface Controller extends Function<Context,Object> {

    public Object action(Context context);

    @Override
    default Object apply(Context context) {
        return action(context);

    }
}

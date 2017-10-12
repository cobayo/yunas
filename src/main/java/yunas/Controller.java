package yunas;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import yunas.Context;

/**
 * yunas.Controller interface for java.
 */
public interface Controller extends Function1<Context,Object> {

    public Object action(Context context);

    @Override
    default Object invoke(Context context) {
        return action(context);

    }
}

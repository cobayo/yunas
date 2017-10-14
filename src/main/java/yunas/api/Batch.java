package yunas.api;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Batch Interface for Java.
 */
public interface Batch extends Function1<String[], Unit> {

    public void action(String[] args);

    @Override
    default Unit invoke(String[] args) {
        action(args);
        return null;
    }
}

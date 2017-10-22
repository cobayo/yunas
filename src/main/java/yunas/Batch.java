package yunas;

import kotlin.Unit;

import java.util.function.Function;

/**
 * Batch Interface for Java.
 */
public interface Batch extends Function<String[], Unit> {

    public void action(String[] args);

    @Override
    default Unit apply(String[] args) {
        action(args);
        return null;
    }
}

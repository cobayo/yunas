package yunas;

import java.util.function.Consumer;

/**
 * Batch Interface for Java.
 */
public interface Batch extends Consumer<String[]> {

    public void action(String[] args);

    @Override
    default void accept(String[] args) {
        action(args);
    }
}

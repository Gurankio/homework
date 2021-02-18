package gurankio.menu.window.interactive;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class InteractiveExit implements Interactive {

    @Override
    public void render(Consumer<String> consumer, Object instance) {
        consumer.accept("exit");
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList("exit", "e", "back");
    }

    @Override
    public Object call(Object instance) {
        return null;
    }
}

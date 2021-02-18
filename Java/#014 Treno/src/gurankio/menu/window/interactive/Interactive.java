package gurankio.menu.window.interactive;

import java.util.List;
import java.util.function.Consumer;

public interface Interactive {

    void render(Consumer<String> consumer, Object instance);
    List<String> getNames();
    Object call(Object instance);

}

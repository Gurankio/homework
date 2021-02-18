package gurankio.menu.window.interactive;

import gurankio.menu.window.Window;
import gurankio.util.TreeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InteractiveWindow implements Interactive {

    private final String name;
    private final Window window;

    public InteractiveWindow(String name, Window window) {
        this.name = name;
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }

    @Override
    public void render(Consumer<String> consumer, Object instance) {
        if (window.getInteractives().size() <= 1) return;
        if (window.getInteractives().size() > 10) {
            consumer.accept(name + "...");
        } else {
            TreeBuilder builder = new TreeBuilder(name);
            window.getInteractives().stream().filter(i -> !(i instanceof InteractiveExit)).forEach(i -> i.render(builder::arrow, instance));
            consumer.accept(builder.end().toString());
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>(window.getInteractivesNames());
        names.add(0, name);
        return names;
    }

    @Override
    public Object call(Object instance) {
        return window;
    }
}

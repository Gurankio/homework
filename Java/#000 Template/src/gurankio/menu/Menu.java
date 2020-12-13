package gurankio.menu;

import gurankio.menu.io.ConsoleInput;
import gurankio.menu.io.ConsoleOutput;
import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;
import gurankio.menu.window.Window;
import gurankio.menu.window.WindowFactory;
import gurankio.menu.window.interactive.Interactive;

import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;

public class Menu {

    private final Map<Class<?>, Window> windows;
    private final Stack<Object> stack;

    public Menu(Supplier<?> supplier) {
        ConsoleOutput.debugln("dyMenu - Alpha MK2");

        Object entrypoint = supplier.get();
        this.windows = WindowFactory.createAll(entrypoint.getClass());
        this.stack = new Stack<>();
        this.stack.push(entrypoint);
    }

    private TreeBuilder getPath() {
        TreeBuilder builder = new TreeBuilder();
        for (Object o : stack) {
            Class<?> target = o instanceof Class<?> ? (Class<?>) o : o.getClass();
            builder.start(StringPrettify.toPrettyString(target));
        }
        return builder;
    }

    public void console() {
        while (!stack.isEmpty()) {
            Object instance = stack.peek();
            Window window;
            if (instance instanceof Window) {
                window = (Window) instance;
                instance = stack.get(stack.size()-2);
            }
            else if (instance instanceof Class<?>) window = windows.get(instance);
            else window = windows.get(instance.getClass());

            Interactive interactive = window.loop(this::getPath, instance);
            ConsoleOutput.println();
            Object next = interactive.call(instance);

            if (next instanceof Window) {
                StringPrettify.instanceOverride.put(next, interactive.getNames().get(0));
                stack.push(next);
            } else {
                if (next != null) {
                    // TODO: do not force to open, check clipboard...
                    ConsoleInput.read("Press ENTER to continue...", false);

                    if (next != stack.peek()) {
                        if ((next instanceof Class<?> && windows.containsKey(next)) || windows.containsKey(next.getClass())) stack.push(next);
                        else ConsoleOutput.println("Dropping \"" + StringPrettify.toPrettyString(next) + "\" as it doesn't have a menu.");
                    }
                } else stack.pop();
            }
        }
    }
}

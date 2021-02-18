package gurankio.menu;

import gurankio.io.text.TextParser;
import gurankio.io.text.TextSerializer;
import gurankio.menu.io.ConsoleIO;
import gurankio.util.TreeBuilder;
import gurankio.menu.window.Window;
import gurankio.menu.window.WindowFactory;
import gurankio.menu.window.interactive.Interactive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Supplier;

public class Menu {

    public static ConsoleIO console = new ConsoleIO();

    private static final List<Class<?>> ignored = new ArrayList<>();

    static {
        ignore(byte.class);
        ignore(short.class);
        ignore(int.class);
        ignore(long.class);
        ignore(float.class);
        ignore(double.class);
        ignore(boolean.class);
        ignore(char.class);
        ignore(Byte.class);
        ignore(Short.class);
        ignore(Integer.class);
        ignore(Long.class);
        ignore(Float.class);
        ignore(Double.class);
        ignore(Boolean.class);
        ignore(Character.class);
        ignore(String.class);
    }

    public static void ignore(Class<?> target) {
        ignored.add(target);
    }

    private Function<Object, Object> onClose;

    public Menu onClose(Function<Object, Object> onClose) {
        this.onClose = this.onClose == null ? onClose : this.onClose.compose(onClose);
        return this;
    }

    private final Map<Class<?>, Window> windows;
    private final Stack<Object> stack;

    public Menu(Supplier<?> supplier) {
        // console.debugln("dyMenu - Alpha MK2");
        if (!Menu.class.getConstructors()[0].getParameters()[0].isNamePresent()) console.println("Compilare con '-parameters' per i nomi dei parametri.");

        Object entrypoint = supplier.get();
        this.windows = WindowFactory.createAll(entrypoint.getClass());
        this.stack = new Stack<>();
        this.stack.push(entrypoint);
    }

    private TreeBuilder getPath() {
        TreeBuilder builder = new TreeBuilder();
        for (Object o : stack) {
            if (o instanceof Window) {
                builder.start(TextSerializer.serialize(o));
            } else {
                Class<?> target = o instanceof Class<?> ? (Class<?>) o : o.getClass();
                builder.start(TextSerializer.serialize(target));
            }
        }
        return builder;
    }

    public void run() {
        while (!stack.isEmpty()) {
            Object instance = stack.peek();
            Window window;
            if (instance instanceof Window) {
                window = (Window) instance;
                instance = stack.get(stack.size()-2);
            }
            else if (instance instanceof Class<?>) window = windows.get(instance);
            else window = windows.get(instance.getClass());

            console.println();
            Interactive interactive = window.loop(this::getPath, instance);
            console.println();
            Object next = interactive.call(instance);

            if (next instanceof Window) {
                TextSerializer.registerInstanceSupplier(next, interactive.getNames().get(0));
                stack.push(next);
            } else {
                if (next != null) {
                    // TODO: do not force to open, check clipboard...
                    console.read("Press ENTER to continue...", String.class);

                    if (next != stack.peek()) {
                        if (!((next instanceof Class<?> && windows.containsKey(next)) || windows.containsKey(next.getClass()))) {
                            // console.debugln(String.format("Building %s", next.getClass().getSimpleName()));
                            Map<Class<?>, Window> temp = WindowFactory.createAll(next.getClass());
                            temp.keySet().stream().filter(k -> !windows.containsKey(k)).forEach(k -> windows.put(k, temp.get(k)));
                        }
                        stack.push(next);
                    }
                } else {
                    if (stack.size() == 1) onClose.apply(stack.peek());
                    stack.pop();
                }
            }

            if (stack.size() > 0 && ignored.stream().anyMatch(c -> c.isInstance(stack.peek()))) {
                if (stack.size() == 1) onClose.apply(stack.peek());
                stack.pop();
            }
        }
    }
}

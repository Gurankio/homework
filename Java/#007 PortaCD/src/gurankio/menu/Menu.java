package gurankio.menu;

import gurankio.menu.input.ConsoleInput;
import gurankio.menu.input.ConsoleOutput;
import gurankio.menu.interaction.Interactable;
import gurankio.menu.window.Window;
import gurankio.menu.window.WindowFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Menu {

    // Built on constructor call. Maps all classes to their window.
    private final Class<?> entrypoint;
    private final Map<Class<?>, Window> menuWindowsMap;

    public Menu(Class<?> entrypoint) {
        ConsoleOutput.println("dyMenu - Alpha");

        this.entrypoint = entrypoint;
        menuWindowsMap = new ConcurrentHashMap<>(); // Build windows.
        List<Class<?>> classList = WindowFactory.searchClasses(entrypoint);
        ConsoleOutput.println("Building menus...");
        ConsoleOutput.incrementIndentation();
        classList.parallelStream()
                .forEach(x -> {
                    menuWindowsMap.put(x, WindowFactory.create(x));
                    ConsoleOutput.println(String.format("Built: %s", x.getSimpleName()));
                });
        ConsoleOutput.decrementIndentation();
        ConsoleOutput.println("Done!");
    }

    public void console() {
        Stack<Object> stack = new Stack<>();
        stack.push(entrypoint);

        while (!stack.isEmpty()) {
            ConsoleOutput.println();

            // Display path
            List<String> strings = new ArrayList<>();
            for (int i=0; i<stack.size(); i++) {
                Class<?> c;
                if (stack.get(i) instanceof Class<?>) c = (Class<?>) stack.get(i);
                else c = stack.get(i).getClass();
                if (i == stack.size()-1) {
                    if (c.isArray()) {
                        strings.add(c.getSimpleName()
                                .replace("[", "")
                                .replace("]", "") + "[" + ((Object[])stack.get(i)).length + "]");
                    } else {
                        try {
                            if (!c.getMethod("toString").getDeclaringClass().getName().startsWith("java")) strings.add(stack.get(i).toString());
                            else strings.add(c.getSimpleName());
                        } catch (NoSuchMethodException e) {
                            strings.add(c.getSimpleName());
                        }
                    }
                } else strings.add(c.getSimpleName());
            }
            ConsoleOutput.path(strings);

            // Display window
            Object instance = stack.peek();
            Window window;
            if (instance instanceof Class<?>) {
                // Static context
                window = menuWindowsMap.get(instance);
            } else {
                // Object context
                window = menuWindowsMap.get(instance.getClass());
            }
            Interactable interactable = window.loop();

            ConsoleOutput.println();
            ConsoleOutput.incrementIndentation();

            Object next = interactable.call(instance);

            if (next != null) {
                ConsoleOutput.print("Press ENTER to continue...");
                ConsoleInput.readString();

                if (next != stack.peek()) {
                    if (next instanceof Class<?>) {
                        // Static context
                        if (menuWindowsMap.containsKey(next)) stack.push(next);
                        else ConsoleOutput.println("Dropping \"" + ((Class<?>) next).getSimpleName() + "\" as it doesn't have a menu.");
                    } else {
                        // Object context
                        if (menuWindowsMap.containsKey(next.getClass())) stack.push(next);
                        else ConsoleOutput.println("Dropping \"" + next.getClass().getSimpleName() + "\" as it doesn't have a menu.");
                    }
                }
            } else stack.pop();

            ConsoleOutput.decrementIndentation();
        }
    }
}

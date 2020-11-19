package gurankio.menu;

import gurankio.menu.interaction.Interactable;
import gurankio.menu.window.Window;
import gurankio.menu.window.WindowFactory;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public class Menu {

    // Built on constructor call. Maps all classes to their window.
    private final Class<?> entrypoint;
    private final Map<Class<?>, Window> menuWindowsMap;

    public Menu(Class<?> entrypoint) {
        System.out.println("dyMenu - Alpha");

        this.entrypoint = entrypoint;
        menuWindowsMap = new ConcurrentHashMap<>(); // Build windows.
        List<Class<?>> classList = WindowFactory.getAllClasses(entrypoint);
        System.out.println("Building menus...");
        classList.parallelStream()
                .forEach(x -> {
                    menuWindowsMap.put(x, WindowFactory.create(x));
                    System.out.printf("Built: %s\n", x.getSimpleName());
                });
        System.out.println("Done!");
    }

    public void start() {
        Stack<Object> stack = new Stack<>();
        stack.push(entrypoint);

        while (!stack.isEmpty()) {
            // Display path
            StringBuilder path = new StringBuilder();
            path.append('\n');
            stack.forEach(x -> {
                if (x instanceof Class<?>) {
                    // Static context
                    path.append(((Class<?>) x).getSimpleName()).append(" -> ");
                } else {
                    // Object context
                    path.append(x.getClass().getSimpleName()).append(" -> ");
                }
            });
            path.delete(path.length() - 4, path.length());
            System.out.println(path);

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
            Object next = interactable.call(instance);

            /*
            TODO: wait for user confirmation
            System.out.print("Press ENTER to continue...");
            ConsoleInput.readString();
             */

            if (next != null) {
                if (next != stack.peek()) {
                    if (next instanceof Class<?>) {
                        // Static context
                        if (menuWindowsMap.containsKey(next)) stack.push(next);
                        else System.out.println("Dropping \"" + ((Class<?>) next).getSimpleName() + "\" as it doesn't have a menu.");
                    } else {
                        // Object context
                        if (menuWindowsMap.containsKey(next.getClass())) stack.push(next);
                        else System.out.println("Dropping \"" + next.getClass().getSimpleName() + "\" as it doesn't have a menu.");
                    }
                }
            } else stack.pop();
        }
    }
}

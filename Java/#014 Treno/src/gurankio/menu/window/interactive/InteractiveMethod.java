package gurankio.menu.window.interactive;

import gurankio.io.text.TextSerializer;
import gurankio.menu.Menu;
import gurankio.util.CharPacks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InteractiveMethod implements Interactive {

    private final Method method;

    public InteractiveMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public void render(Consumer<String> consumer, Object instance) {
        if (method.getReturnType() == void.class || method.getParameters().length != 0) consumer.accept(TextSerializer.serialize(method));
        else {
            try {
                String name = TextSerializer.serialize(method);
                String value = TextSerializer.serialize(method.invoke(instance)).lines()
                        .map(line -> CharPacks.selected.getSpacer(name.length() + 3) + line)
                        .collect(Collectors.joining("\n"))
                        .stripLeading();
                consumer.accept(name + " = " + value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                consumer.accept(TextSerializer.serialize(method));
                // e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList(method.getName());
    }

    @Override
    public Object call(Object instance) {
        Menu.console.println("Calling '" + TextSerializer.serialize(method) + "'");
        try {
            Menu.console.incrementIndentation();
            Object o = method.invoke(
                    instance,
                    Arrays.stream(method.getParameters())
                            .map(Menu.console::read)
                            .toArray()
            );
            Menu.console.decrementIndentation();
            if (o != null) {
                Menu.console.arrowln(o);
                return o;
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return instance;
    }
}

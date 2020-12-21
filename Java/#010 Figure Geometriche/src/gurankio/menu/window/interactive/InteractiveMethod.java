package gurankio.menu.window.interactive;

import gurankio.menu.io.ConsoleInput;
import gurankio.menu.io.ConsoleOutput;
import gurankio.menu.io.util.StringPrettify;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

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
        if (method.getParameters().length != 0) consumer.accept(StringPrettify.toPrettyString(method));
        else {
            try {
                consumer.accept(StringPrettify.toPrettyString(method) + " = " + StringPrettify.toPrettyString(method.invoke(instance)));
            } catch (IllegalAccessException | InvocationTargetException e) {
                consumer.accept(StringPrettify.toPrettyString(method));
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
        ConsoleOutput.println("Calling '" + StringPrettify.toPrettyString(method) + "'");
        try {
            Object o = method.invoke(
                    instance,
                    Arrays.stream(method.getParameters())
                            .map(ConsoleInput::read)
                            .toArray()
            );
            if (o != null) {
                ConsoleOutput.arrowln(o);
                return o;
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return instance;
    }
}

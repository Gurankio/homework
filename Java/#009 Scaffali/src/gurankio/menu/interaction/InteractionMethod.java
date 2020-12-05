package gurankio.menu.interaction;

import gurankio.menu.input.ConsoleOutput;
import gurankio.menu.input.GenericFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class InteractionMethod implements Interactable {

    private final Method method;

    public InteractionMethod(Method method) {
        this.method = method;
    }

    @Override
    public Object call(Object instance) {
        ConsoleOutput.println("Calling '" + method.toGenericString() + "'");
        try {
            Object o = method.invoke(
                    instance,
                    // TODO: clipboard, probably inside GenericFactory as a static field.
                    Arrays.stream(method.getParameters())
                            .map(x -> GenericFactory.create(x.getType()))
                            .toArray()
            );
            if (o != null) {
                // TODO: do not force to open...
                ConsoleOutput.println("─> ", o);
                return o;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return instance;
    }
}

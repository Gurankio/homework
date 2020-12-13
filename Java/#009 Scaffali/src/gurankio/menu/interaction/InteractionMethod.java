package gurankio.menu.interaction;

import gurankio.menu.io.ConsoleOutput;
import gurankio.menu.io.GenericFactory;
import gurankio.menu.io.StringRepresentation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class InteractionMethod implements Interactable {

    private final Method method;

    public InteractionMethod(Method method) {
        this.method = method;
    }

    @Override
    public Object call(Object instance) {
        ConsoleOutput.println("Calling '" + StringRepresentation.toPrettyString(method) + "'");
        try {
            Object o = method.invoke(
                    instance,
                    Arrays.stream(method.getParameters())
                            .map(x -> GenericFactory.create((x.isNamePresent() ? x.getName() : x.getType().getSimpleName()), x.getType()))
                            .toArray()
            );
            if (o != null) {
                ConsoleOutput.println("─> ", o);
                return o;
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return instance;
    }
}

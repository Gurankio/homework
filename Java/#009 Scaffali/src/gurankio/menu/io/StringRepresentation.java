package gurankio.menu.io;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static gurankio.menu.io.ConsoleOutput.*;

// TODO: Handles annotations
public class StringRepresentation {

    public static String toPrettyString(Object object) {
        return object.toString();
    }

    // Fields
    // TODO: arrays
    public static String toPrettyString(Field field) {
        return field.getName() + ": " + field.getType().getSimpleName();
    }

    // Methods
    public static String toPrettyString(Method method) {
        return method.getName() + "(" + Arrays.stream(method.getParameters()).map(p -> (p.isNamePresent() ? p.getName() + ": " : "") + p.getType().getSimpleName()).collect(Collectors.joining(", ")) + "): " + method.getReturnType().getSimpleName();
    }

    public static String toPrettyString(Constructor<?> constructor) {
        return constructor.getDeclaringClass().getSimpleName() + "(" + Arrays.stream(constructor.getParameters()).map(p -> (p.isNamePresent() ? p.getName() + ": " : "") + p.getType().getSimpleName()).collect(Collectors.joining(", ")) + ")";
    }

    // Path
    // TODO: should return string value...
    public static void path(String prefix, List<?> objects) {
        pushIndentation();
        for (int i = 0; i < objects.size(); i++) {
            println(i == 0 ? "" : prefix, objects.get(i));
            incrementIndentation();
        }
        popIndentation();
    }

    public static void path(List<?> objects) {
        path("└> ", objects);
    }

}

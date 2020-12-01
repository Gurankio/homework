package gurankio.menu.input;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericFactory {

    // TODO: better i/o.
    public static Object create(Class<?> aClass){
        // Array
        if (aClass.isArray()) {
            Class<?> type = aClass.getComponentType();
            ConsoleOutput.println("Creating a" + aClass.getComponentType() + " array. Insert size.");
            Integer size = (Integer) create(Integer.class);
            return array(type, size);
        }

        // Primitives
        if (aClass.isPrimitive()) {
            return create(wrapper(aClass));
        }

        // String: doesn't have a String valueOf(String) only valueOf(char[])
        if (aClass.equals(String.class)) return ConsoleInput.readString("String: ");

        // valueOf method, handles Wrapper classes + Enums.
        /* if (aClass.isEnum()) {
            ConsoleOutput.print(aClass);
        } */

        try {
            return aClass
                    .getMethod("valueOf", String.class)
                    .invoke(aClass, ConsoleInput.readString(aClass.getSimpleName() + ": "));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }

        // Recursive constructor calls for everything else.
        Constructor<?>[] constructors = aClass.getConstructors();
        if (constructors.length == 0) throw new IllegalArgumentException("Non generate-able class. (" + aClass + ")");

        ConsoleOutput.println("Found " + constructors.length + " constructor" + (constructors.length == 1 ? "." : "s."));
        int choice = 1;
        if (constructors.length != 1) {
            for (int i=0; i<constructors.length; i++) {
                ConsoleOutput.println(String.format(" %c> (%02d) %s", (i == constructors.length-1 ? '└' : '├'), i + 1, constructors[i].toGenericString()));
            }
            do {
                choice = ConsoleInput.readInt("Input:");
                if (choice < 1 || choice >= constructors.length) ConsoleOutput.println("Invalid input.");
            } while (choice < 1 || choice >= constructors.length);
        } else {
            ConsoleOutput.println(constructors[choice-1].toGenericString());
        }
        Constructor<?> chosen = constructors[choice-1];
        List<?> parameters = Stream.of(chosen.getParameters())
                .map(x -> create(x.getType()))
                .collect(Collectors.toList());
        try {
            return chosen.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
        }

        // Some strange case?
        throw new IllegalArgumentException("Non generate-able class. (" + aClass + ")");
    }

    // Veramente malvagio.
    @SuppressWarnings("unchecked")
    private static <X> X[]array(Class <X> aClass, int size) {
        if (aClass.isPrimitive()) throw new IllegalArgumentException("Non generate-able class. (" + aClass + ")");
        X[] array = (X[]) Array.newInstance(aClass, size);
        for (int i = 0; i < size; i++) array[i] = (X) create(aClass);
        return array;
    }

    // Malvagio.
    private static Class<?> wrapper(Class <?> aClass) {
        switch (aClass.getName()) {
            case "boolean":
                return Boolean.class;
            case "byte":
                return Byte.class;
            case "char":
                return Character.class;
            case "short":
                return Short.class;
            case "int":
                return Integer.class;
            case "long":
                return Long.class;
            case "float":
                return Float.class;
            case "double":
                return Double.class;
            default:
                throw new IllegalArgumentException("Class passed to wrapper is not primitive.");
        }
    }
}
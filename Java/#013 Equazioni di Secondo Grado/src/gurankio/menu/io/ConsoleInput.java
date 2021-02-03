package gurankio.menu.io;

import gurankio.menu.io.util.StringPrettify;
import gurankio.menu.io.util.TreeBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: add math
// TODO: add clipboard
public class ConsoleInput {

    public static final String ERROR_MESSAGE = "Invalid input.";
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static String read(String prompt) {
        return read(prompt, true);
    }

    public static String read(String prompt, boolean spacer) {
        ConsoleOutput.print(prompt + (spacer ? ": " : ""));
        try {
            return READER.readLine();
        } catch (IOException e) {
            ConsoleOutput.println(ERROR_MESSAGE);
            return read(prompt);
        }
    }

    public static Object read(String prompt, Class<?> target) {
        // Primitives
        if (target.isPrimitive()) return read(prompt, wrapper(target));

        Object r = null;
        ConsoleOutput.incrementIndentation();

        // Array
        if (target.isArray()) {
            ConsoleOutput.println("Creating " + StringPrettify.toPrettyString(target) + "...");
            int size = (Integer) read("size", Integer.class);
            r = Array.newInstance(target.getComponentType(), size);
            for (int i = 0; i < size; i++) ((Object[]) r)[i] = read(prompt + "[" + i + "]", target.getComponentType());
            ConsoleOutput.decrementIndentation();
            return r;
        }

        // String: doesn't have a String valueOf(String) only valueOf(char[])
        if (target.equals(String.class)) {
            r = read("String " + prompt);
            ConsoleOutput.decrementIndentation();
            return r;
        }

        Method valueOf = null;
        try {
            valueOf = target.getMethod("valueOf", String.class);
        } catch (NoSuchMethodException ignored) {
            // This is fine, if 'valueOf' does not exists, we can call one of target's constructor.
            // valueOf method is implemented by default in wrapper classes and enums.
            // Provide a custom implementation to speed up your class generation.
        }

        if (valueOf != null) {
            if (target.isEnum()) {
                try {
                    ConsoleOutput.println("May be one of " + Arrays.toString((Object[]) target.getMethod("values").invoke(target)).replace("[", "").replace("]", ""));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                }
            }
            do {
                try {
                    r = valueOf.invoke(target, read(StringPrettify.toPrettyString(target) + " " + prompt));
                } catch (IllegalAccessException | InvocationTargetException ignored) {
                    ConsoleOutput.println(ConsoleInput.ERROR_MESSAGE);
                }
            } while (r == null);

            ConsoleOutput.decrementIndentation();
            return r;
        }

        ConsoleOutput.println("Creating " + StringPrettify.toPrettyString(target) + " " + prompt + "...");

        // Recursive constructor calls for everything else.
        if (Modifier.isAbstract(target.getModifiers())) {
            throw new IllegalArgumentException(target + " can not be crated with user input. Provide a 'valueOf(String)' method.");
        }

        List<Constructor<?>> constructors = Arrays.stream(target.getConstructors())
                    .sorted(Comparator.comparing(Constructor::getParameterCount))
                    .collect(Collectors.toList());

        if (constructors.size() != 0) {
            TreeBuilder builder = new TreeBuilder("Found " + constructors.size() + " constructor" + (constructors.size() == 1 ? ":" : "s:"));
            constructors.stream().map(StringPrettify::toPrettyString).forEach(builder::arrowCounted);
            ConsoleOutput.println(builder.end().toString());
            int choice = 1;
            if (constructors.size() != 1) {
                do {
                    ConsoleOutput.decrementIndentation();
                    choice = (int) ConsoleInput.read("Choice", Integer.class);
                    ConsoleOutput.incrementIndentation();
                    if (choice < 1 || choice > constructors.size()) ConsoleOutput.println("Invalid input. Provide a number between 1 and " + constructors.size());
                } while (choice < 1 || choice > constructors.size());
            }
            Constructor<?> chosen = constructors.get(choice - 1);
            List<?> parameters = Stream.of(chosen.getParameters())
                    .map(ConsoleInput::read)
                    .collect(Collectors.toList());
            try {
                ConsoleOutput.decrementIndentation();
                return chosen.newInstance(parameters.toArray());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        ConsoleOutput.decrementIndentation();
        throw new IllegalArgumentException(target + " can not be crated with user input. Provide a 'valueOf(String)' method.");
    }

    public static Object read(Field field) {
        return read(StringPrettify.toPrettyString(field), field.getType());
    }

    public static Object read(Parameter parameter) {
        return read(StringPrettify.toPrettyString(parameter), parameter.getType());
    }

    // Bad.
    private static Class<?> wrapper(Class <?> target) {
        switch (target.getName()) {
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
                throw new IllegalArgumentException(target + " is not primitive.");
        }
    }

}

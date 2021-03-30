package gurankio.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

// TODO: bounds
public class GenericScanner {

    // Parser
    // A parser may throw any RuntimeException
    private static final HashMap<Class<?>, Function<String, ?>> parsers = new HashMap<>();

    public static <T> void registerParser(Class<T> target, Function<String, T> parser) {
        parsers.put(target, parser);
    }

    static {
        registerParser(byte.class, Byte::parseByte);
        registerParser(short.class, Short::parseShort);
        registerParser(int.class, Integer::parseInt);
        registerParser(long.class, Long::parseLong);
        registerParser(float.class, Float::parseFloat);
        registerParser(double.class, Double::parseDouble);
        registerParser(boolean.class, Boolean::parseBoolean);
        registerParser(char.class, s -> s.charAt(0));
        registerParser(Byte.class, Byte::parseByte);
        registerParser(Short.class, Short::parseShort);
        registerParser(Integer.class, Integer::parseInt);
        registerParser(Long.class, Long::parseLong);
        registerParser(Float.class, Float::parseFloat);
        registerParser(Double.class, Double::parseDouble);
        registerParser(Boolean.class, Boolean::parseBoolean);
        registerParser(Character.class, s -> s.charAt(0));
        registerParser(String.class, String::strip);
    }

    @SuppressWarnings("unchecked") // registerParser makes sure that key and value match.
    public static <T> Optional<T> parse(String string, Class<T> type) {
        try {
            return Optional.of((T) parsers.get(type).apply(string));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    // Input

    private static final Scanner scanner = new Scanner(System.in);

    public static void confirm() {
        Logger.DIRECT.print("...");
        scanner.nextLine();
    }

    public static String next(String prompt) {
        Logger.DIRECT.println(Logger.wrap(prompt, " - "));
        Logger.DIRECT.print(" > ");
        return scanner.nextLine();
    }

    // Required type or null.
    public static <T> Optional<T> next(Class<T> type, String prompt) {
        // Known serialization.
        if (parsers.containsKey(type)) {
            String input = next(prompt);
            return parse(input, type);
        }

        if (type.isArray()) {


            return Optional.empty();
        }

        if (type.isEnum()) {
            T[] ts = type.getEnumConstants();

            return Optional.empty();
        }

        return constructor(type);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.CONSTRUCTOR)
    public @interface Prompts {
        String[] value();
    }

    @SuppressWarnings("unchecked") // not unchecked as getConstructors is dumb.
    private static <T> Optional<T> constructor(Class<T> type) {
        try {
            Constructor<T> constructor = (Constructor<T>) Arrays.stream(type.getConstructors())
                    .filter(c -> Arrays.stream(c.getAnnotations())
                            .anyMatch(a -> a.annotationType().equals(Prompts.class)))
                    .findFirst()
                    .orElseThrow(); // TODO: custom exception and handling.

            String[] prompts = constructor.getAnnotation(Prompts.class).value();
            Parameter[] parameters = constructor.getParameters();

            // TODO: assert prompts.length == parameters.length;
            Object[] values = IntStream.range(0, parameters.length)
                    .mapToObj(i -> next(parameters[i].getType(), prompts[i]))
                    .toArray();

            return Optional.of(constructor.newInstance(values));
        } catch (Exception e) {
            // TODO: proper handling.
            return Optional.empty();
        }
    }
}

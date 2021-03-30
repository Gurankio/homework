package gurankio.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

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

    // Constructors

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
                    .orElseThrow(MissingPromptConstructor::new);

            String[] prompts = constructor.getAnnotation(Prompts.class).value();
            Parameter[] parameters = constructor.getParameters();

            if (prompts.length == parameters.length) {
                Logger.DEBUG.println("@Prompts annotated constructor for '" + type.getSimpleName() + "' has a wrong number of prompts. (" + prompts.length + " != " + parameters.length + ")");
                return Optional.empty();
            }

            Object[] values = IntStream.range(0, parameters.length)
                    .mapToObj(i -> next(parameters[i].getType(), prompts[i]))
                    .toArray();

            return Optional.of(constructor.newInstance(values));
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            Logger.DEBUG.exception(e);
            return Optional.empty();
        } catch (MissingPromptConstructor e) {
            Logger.DEBUG.println("Missing @Prompts annotated constructor for '" + type.getSimpleName() + "'.");
            return Optional.empty();
        }
    }

    private static class MissingPromptConstructor extends Exception {
        // NO-OP
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
    @SuppressWarnings("unchecked") // not unchecked as we use Array::newInstance.
    private static <T> Optional<T> next(Class<T> type, String prompt) {
        String input = next(prompt);

        // Known serialization.
        if (parsers.containsKey(type)) {
            return parse(input, type);
        }

        if (type.isArray()) {
            String[] tokens = input.replaceFirst("\\[", "").replaceFirst("](?!.*?])", "").split(","); // TODO: does not work with more than one dimension.
            Object array = Array.newInstance(type.getComponentType(), tokens.length);
            for (int i = 0; i < tokens.length; i++) Array.set(array, i, parse(tokens[i], type.getComponentType()));
            return Optional.of((T) array);
        }

        if (type.isEnum()) {
            return Arrays.stream(type.getEnumConstants())
                    .filter(t -> t.toString().equalsIgnoreCase(input))
                    .findFirst();
        }

        return constructor(type);
    }

    // check to see if it is IN bounds
    public static <T> T next(Class<T> type, String prompt, Predicate<T> checker, String error) {
        Optional<T> t = next(type, prompt);
        if (t.isPresent() && checker.test(t.get())) {
            return t.get();
        } else {
            Logger.WARN.println(error);
            confirm();
            return next(type, prompt, checker, error);
        }
    }

    public static <T> T next(Class<T> type, String prompt, String error) {
        return next(type, prompt, (t) -> true, error);
    }

    // Utilities

    // check to see if next is within [start, end)
    public static <T extends Comparable<T>> T nextRange(Class<T> type, String prompt, T start, T end) {
        return next(type, prompt, t -> t.compareTo(start) >= 0 && t.compareTo(end) < 0, "Valore invalido. Inserire un valore tra " + start + " e " + end + ".");
    }

}

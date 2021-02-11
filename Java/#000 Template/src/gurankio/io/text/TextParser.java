package gurankio.io.text;

import gurankio.io.exception.InvalidInputException;
import gurankio.io.exception.MissingSupplierException;
import gurankio.io.exception.SupplierTypeMismatchException;
import gurankio.util.JShellEvalWrapper;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class TextParser {

    private static final HashMap<Class<?>, Function<String, ?>> suppliers = new HashMap<>();

    /**
     * Makes sure to add only matching keys and function.
     *
     * @param target   The class to register.
     * @param function The associated supplier.
     * @param <T>      The registered type.
     */
    public static <T> void registerSupplier(Class<T> target, Function<String, T> function) {
        suppliers.put(target, function);
    }

    public static boolean hasSupplier(Class<?> target) {
        return suppliers.containsKey(target);
    }

    // Static initializer for the suppliers.
    static {
        registerSupplier(byte.class, TextParser::getByte);
        registerSupplier(short.class, TextParser::getShort);
        registerSupplier(int.class, TextParser::getInteger);
        registerSupplier(long.class, TextParser::getLong);
        registerSupplier(float.class, TextParser::getFloat);
        registerSupplier(double.class, TextParser::getDouble);
        registerSupplier(boolean.class, TextParser::getBoolean);
        registerSupplier(char.class, TextParser::getCharacter);
        registerSupplier(Byte.class, TextParser::getByte);
        registerSupplier(Short.class, TextParser::getShort);
        registerSupplier(Integer.class, TextParser::getInteger);
        registerSupplier(Long.class, TextParser::getLong);
        registerSupplier(Float.class, TextParser::getFloat);
        registerSupplier(Double.class, TextParser::getDouble);
        registerSupplier(Boolean.class, TextParser::getBoolean);
        registerSupplier(Character.class, TextParser::getCharacter);
        registerSupplier(String.class, TextParser::getString);
    }

    /**
     * Given data from the user, builds an instance of given class.
     *
     * @param target The class to build.
     * @param data   The string to parse.
     * @param <T>    The type of the class to build.
     * @return An instance of type T built with user input.
     * @throws MissingSupplierException      Occurs when a class its trying to be created but there are no suitable suppliers.
     * @throws SupplierTypeMismatchException Occurs when the suppliers map has a mismatch between key and return type.
     *                                       As long as the suppliers key and return type match, there is going to be no exception.
     */
    @SuppressWarnings("unchecked")
    public static <T> T parse(Class<T> target, String data) throws MissingSupplierException, SupplierTypeMismatchException {
        if (target.isArray()) {
            return (T) Stream.of(data.replaceAll("\\[(?=\\[)|](?!.*?])", "").split(","))
                    .map(String::strip)
                    .map(c -> parse(target.getComponentType(), c))
                    .toArray();
        }

        if (target.isEnum()) {
            return Stream.of(target.getEnumConstants())
                    .filter(c -> c.toString().equalsIgnoreCase(data))
                    .findFirst()
                    .orElseThrow();
        }

        try {
            Function<String, ?> supplier = suppliers.get(target);
            if (supplier == null) throw new MissingSupplierException(target);
            return (T) supplier.apply(data.strip()); // Strip for good measure.
        } catch (ClassCastException exception) {
            throw new SupplierTypeMismatchException(target);
        }
    }

    private static Byte getByte(String data) {
        try {
            return Byte.parseByte(JShellEvalWrapper.eval(data));
        } catch (NumberFormatException exception) {
            throw new InvalidInputException();
        }
    }

    private static Short getShort(String data) {
        try {
            return Short.parseShort(JShellEvalWrapper.eval(data));
        } catch (NumberFormatException exception) {
            throw new InvalidInputException();
        }
    }

    private static Integer getInteger(String data) {
        try {
            return Integer.parseInt(JShellEvalWrapper.eval(data));
        } catch (NumberFormatException exception) {
            throw new InvalidInputException();
        }
    }

    private static Long getLong(String data) {
        try {
            return Long.parseLong(JShellEvalWrapper.eval(data));
        } catch (NumberFormatException exception) {
            throw new InvalidInputException();
        }
    }

    private static Float getFloat(String data) {
        try {
            return Float.parseFloat(JShellEvalWrapper.eval(data));
        } catch (NumberFormatException exception) {
            throw new InvalidInputException();
        }
    }

    private static Double getDouble(String data) {
        try {
            return Double.parseDouble(JShellEvalWrapper.eval(data));
        } catch (NumberFormatException exception) {
            throw new InvalidInputException();
        }
    }

    private static Boolean getBoolean(String data) {
        return data.startsWith("y") || data.startsWith("Y");
    }

    private static Character getCharacter(String data) {
        return data.charAt(0);
    }

    private static String getString(String data) {
        return data;
    }

}

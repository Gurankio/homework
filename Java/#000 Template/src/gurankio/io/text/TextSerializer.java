package gurankio.io.text;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextSerializer {

    private static final HashMap<Object, String> instanceSupplier = new HashMap<>();
    private static final HashMap<Class<?>, Function<Object, String>> classSuppliers = new HashMap<>();

    /**
     * Makes sure to add only matching keys and function.
     *
     * @param target   The object to register.
     * @param string   The associated string.
     */
    public static void registerInstanceSupplier(Object target, String string) {
        instanceSupplier.remove(target);
        instanceSupplier.put(target, string);
    }

    /**
     * Makes sure to add only matching keys and function.
     *
     * @param target   The class to register.
     * @param function The associated supplier.
     * @param <T>      The registered type.
     */
    @SuppressWarnings("unchecked")
    public static <T> void registerClassSupplier(Class<T> target, Function<T, String> function) {
        classSuppliers.remove(target);
        // Casting to Object, should not give an exception.
        classSuppliers.put(target, (Function<Object, String>) function);
    }

    public static boolean hasClassSupplier(Class<?> target) {
        return classSuppliers.containsKey(target);
    }

    // Static initializer for the suppliers.
    static {
        registerClassSupplier(byte.class, TextSerializer::fromByte);
        registerClassSupplier(short.class, TextSerializer::fromShort);
        registerClassSupplier(int.class, TextSerializer::fromInt);
        registerClassSupplier(long.class, TextSerializer::fromLong);
        registerClassSupplier(float.class, TextSerializer::fromFloat);
        registerClassSupplier(double.class, TextSerializer::fromDouble);
        registerClassSupplier(boolean.class, TextSerializer::fromBoolean);
        registerClassSupplier(char.class, TextSerializer::fromChar);
        registerClassSupplier(Byte.class, TextSerializer::fromByte);
        registerClassSupplier(Short.class, TextSerializer::fromShort);
        registerClassSupplier(Integer.class, TextSerializer::fromInt);
        registerClassSupplier(Long.class, TextSerializer::fromLong);
        registerClassSupplier(Float.class, TextSerializer::fromFloat);
        registerClassSupplier(Double.class, TextSerializer::fromDouble);
        registerClassSupplier(Boolean.class, TextSerializer::fromBoolean);
        registerClassSupplier(Character.class, TextSerializer::fromChar);
        registerClassSupplier(String.class, String::toString);
        // registerClassSupplier(ArrayList.class, TextSerializer::fromArrayList);
        registerClassSupplier(Class.class, TextSerializer::fromClass);
        registerClassSupplier(Field.class, TextSerializer::fromField);
        registerClassSupplier(Parameter.class, TextSerializer::fromParameter);
        registerClassSupplier(Method.class, TextSerializer::fromMethod);
        registerClassSupplier(Constructor.class, TextSerializer::fromConstructor);
    }

    /**
     * Serializes a given object.
     *
     * @param object The object to serialize.
     * @return       A string representation of the object.
     */
    public static String serialize(Object object) {
        if (object == null) return "null";
        if (instanceSupplier.containsKey(object)) return instanceSupplier.get(object);
        if (classSuppliers.containsKey(object.getClass())) return classSuppliers.get(object.getClass()).apply(object);
        if (object.getClass().isArray()) return serialize(Arrays.asList(((Object[]) object)));
        return object.toString();
    }

    private static String fromArrayList(ArrayList<?> array) {
        return "[\n" + array.stream().map(TextSerializer::serialize).collect(Collectors.joining(",\n")) + "\n]";
    }

    private static String fromClass(Class<?> target) {
        return target.getSimpleName();
    }

    private static String fromField(Field field) {
        return field.getName() + (field.getClass().isArray() ? "[]" : "");
    }

    private static String fromParameter(Parameter parameter) {
        return parameter.isNamePresent() ? parameter.getName() : "parameter";
    }

    private static String fromMethod(Method method) {
        return method.getName() + "(" + Arrays.stream(method.getParameters()).map(TextSerializer::serialize).collect(Collectors.joining(", ")) + ")";
    }

    private static String fromConstructor(Constructor<?> constructor) {
        return constructor.getDeclaringClass().getSimpleName() + "(" + Arrays.stream(constructor.getParameters()).map(TextSerializer::serialize).collect(Collectors.joining(", ")) + ")";
    }

    private static String fromByte(Byte b) {
        return Byte.toString(b);
    }

    private static String fromShort(short s) {
        return Short.toString(s);
    }

    private static String fromInt(int i) {
        return Integer.toString(i);
    }

    private static String fromLong(long l) {
        return Long.toString(l);
    }

    private static String fromFloat(float f) {
        return Float.toString(f);
    }

    private static String fromDouble(double d) {
        return Double.toString(d);
    }

    private static String fromBoolean(boolean b) {
        return Boolean.toString(b);
    }

    private static String fromChar(char c) {
        return Character.toString(c);
    }

}

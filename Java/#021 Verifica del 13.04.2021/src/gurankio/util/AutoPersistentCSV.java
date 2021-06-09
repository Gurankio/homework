package gurankio.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static gurankio.util.AutoPersistentCSV.PersistenceDelegateHandler.*;

public interface AutoPersistentCSV extends PersistentCSV {

    class PersistenceDelegateHandler {

        private static record PersistenceDelegate<T>(Function<T, String> serializer, Function<String, T> parser) {};
        private static final Map<Class<?>, PersistenceDelegate<?>> persistenceDelegates = new ConcurrentHashMap<>();

        public static <T> void registerPersistenceDelegate(Class<T> target, Function<T, String> serializer, Function<String, T> parser) {
            persistenceDelegates.put(target, new PersistenceDelegate<>(serializer, parser));
        }

        public static boolean hasPersistenceDelegate(Class<?> target) {
            return persistenceDelegates.containsKey(target) || target.isEnum();
        }

        @SuppressWarnings("unchecked")
        public static <T> String delegatedSerialization(T data) {
            Class<?> target = data.getClass();
            if (target.isEnum()) return data.toString();
            return ((Function<T, String>) persistenceDelegates.get(target).serializer()).apply(data);
        }

        @SuppressWarnings("unchecked")
        public static <T> T delegatedParsing(String data, Class<T> target) {
            if (target.isEnum()) return Arrays.stream(target.getEnumConstants()).filter(constant -> constant.toString().equals(data)).findFirst().orElse(null);
            return ((Function<String, T>) persistenceDelegates.get(target).parser()).apply(data);
        }

        static {
            registerPersistenceDelegate(byte.class, Object::toString, Byte::parseByte);
            registerPersistenceDelegate(short.class, Object::toString, Short::parseShort);
            registerPersistenceDelegate(int.class, Object::toString, Integer::parseInt);
            registerPersistenceDelegate(long.class, Object::toString, Long::parseLong);
            registerPersistenceDelegate(float.class, Object::toString, Float::parseFloat);
            registerPersistenceDelegate(double.class, Object::toString, Double::parseDouble);
            registerPersistenceDelegate(boolean.class, Object::toString, Boolean::parseBoolean);
            registerPersistenceDelegate(char.class, Object::toString, s -> s.charAt(0));
            registerPersistenceDelegate(Byte.class, Object::toString, Byte::parseByte);
            registerPersistenceDelegate(Short.class, Object::toString, Short::parseShort);
            registerPersistenceDelegate(Integer.class, Object::toString, Integer::parseInt);
            registerPersistenceDelegate(Long.class, Object::toString, Long::parseLong);
            registerPersistenceDelegate(Float.class, Object::toString, Float::parseFloat);
            registerPersistenceDelegate(Double.class, Object::toString, Double::parseDouble);
            registerPersistenceDelegate(Boolean.class, Object::toString, Boolean::parseBoolean);
            registerPersistenceDelegate(Character.class, Object::toString, s -> s.charAt(0));
            registerPersistenceDelegate(String.class, Object::toString, String::strip);
            registerPersistenceDelegate(LocalDateTime.class, LocalDateTime::toString, LocalDateTime::parse);
            registerPersistenceDelegate(LocalDate.class, LocalDate::toString, LocalDate::parse);
            registerPersistenceDelegate(LocalTime.class, LocalTime::toString, LocalTime::parse);
        }

    }

    default void serialize(Queue<String> tokens) {
        try {
            BeanInfo info = Introspector.getBeanInfo(getClass());
            List<PropertyDescriptor> propertyDescriptors = Arrays.stream(info.getPropertyDescriptors()).collect(Collectors.toList());

            for (PropertyDescriptor descriptor : propertyDescriptors) {
                if (descriptor.getReadMethod().getName().equals("getClass")) continue;
                if (descriptor.getReadMethod() == null || descriptor.getWriteMethod() == null) continue;

                Method getter = descriptor.getReadMethod();
                Class<?> target = getter.getReturnType();
                Object value = getter.invoke(this);

                if (hasPersistenceDelegate(target)) {
                    tokens.add(delegatedSerialization(value));
                } else if (PersistentCSV.class.isAssignableFrom(target)) {
                    tokens.add(value.getClass().getName());
                    ((PersistentCSV) value).serialize(tokens);
                } else {
                    tokens.add(null);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    default boolean parse(Queue<String> tokens) {
        try {
            BeanInfo info = Introspector.getBeanInfo(getClass());
            List<PropertyDescriptor> propertyDescriptors = Arrays.stream(info.getPropertyDescriptors()).collect(Collectors.toList());

            for (PropertyDescriptor descriptor : propertyDescriptors) {
                if (descriptor.getReadMethod().getName().equals("getClass")) continue;
                if (descriptor.getReadMethod() == null || descriptor.getWriteMethod() == null) continue;

                Method setter = descriptor.getWriteMethod();
                Class<?> target = setter.getParameters()[0].getType();

                if (hasPersistenceDelegate(target)) {
                    setter.invoke(this, delegatedParsing(tokens.remove(), target));
                } else if (PersistentCSV.class.isAssignableFrom(target)) {
                    PersistentCSV persistentCSV = (PersistentCSV) Class.forName(tokens.remove()).getConstructor().newInstance();
                    persistentCSV.parse(tokens);
                    setter.invoke(this, persistentCSV);
                }
            }
            return true;
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
            return false;
        }
    }

}

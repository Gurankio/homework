package gurankio.input;

import gurankio.Cd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleMenu<T> {

    Map<Class<?>, ConsoleMenuPage<?>> menuPageMap;

    List<Class<?>> typeInputMap;

    public ConsoleMenu(Class<T> aClass) {
        typeInputMap = new ArrayList<>();
        typeInputMap.add(String.class);
        typeInputMap.add(Integer.class);
        typeInputMap.add(Double.class);
        List<Class<?>> types = Stream.of(aClass.getFields())
                .filter(x -> !x.getDeclaringClass().getTypeName().startsWith("java"))
                .map(Field::getType)
                .collect(Collectors.toList());
        Stream.of(aClass.getMethods())
                .filter((x) -> !x.getDeclaringClass().getTypeName().startsWith("java"))
                .filter((x) -> !x.getName().equals("main"))
                .map(x -> Arrays.stream(x.getParameters()))
                .collect(Collectors.toList());

        System.out.println(read(int.class));
        System.out.println(read(Double.class));
        System.out.println(read(String.class));
        System.out.println(read(Cd.class));


    }

    public Object read(Class<?> aClass) {
        if (aClass.isPrimitive()) {
            return aClass.
        }
        if (aClass.equals(String.class)) return ConsoleInput.readString("String: "); // Special case...?

        try {
            return aClass
                    .getMethod("valueOf", String.class)
                    .invoke(aClass, ConsoleInput.readString(aClass.getSimpleName()+ ": "));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {}
        // valueOf failed, call constructor.
        Constructor<?>[] constructors = aClass.getConstructors();
        Constructor<?> chosen = constructors[0]; // TODO: Ask the user which one.
        System.out.println(chosen.toGenericString());
        List<?> parameters = Stream.of(chosen.getParameters())
                .map(x -> read(x.getType()))
                .collect(Collectors.toList());
        try {
            return chosen.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignored) {}
        return null;
    }

    // Main loop.
    public void start() {

    }

    private void clearScreen() {
        for (int i=0; i<50; i++) System.out.printf("%n");
    }

    private static class ConsoleMenuPage<T> {

        private Map<Integer, ConsoleMenuPage<Object>> pages;
        private final String menu;

        public ConsoleMenuPage(String menu) {
            this.menu = menu;
            // pages = new ArrayList<>();
        }
    }

}

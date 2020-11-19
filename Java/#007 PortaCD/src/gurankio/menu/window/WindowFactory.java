package gurankio.menu.window;

import gurankio.menu.interaction.InteractionField;
import gurankio.menu.interaction.InteractionMethod;
import gurankio.menu.interaction.Interactable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

// TODO: static / non-static, how to know if it will be accessible?
// TODO: hide + shortcut + menu order
public class WindowFactory {

    // TODO: rename
    public static List<Class<?>> getAllClasses(Class<?> aClass) {
        List<Class<?>> result = new ArrayList<>();

        Stack<Class<?>> classStack = new Stack<>();
        classStack.push(aClass);

        while (!classStack.isEmpty()) {
            Class<?> pClass = classStack.pop();
            result.add(pClass);
            Stream<Class<?>> fields = Arrays.stream(pClass.getFields())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .map(Field::getType);
            Stream<Class<?>> parameters = Arrays.stream(pClass.getMethods())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> !x.getName().equals("main"))
                    .map(Method::getParameters)
                    .flatMap(Arrays::stream)
                    .map(Parameter::getType);
            Stream<Class<?>> returns = Arrays.stream(pClass.getMethods())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> !x.getName().equals("main"))
                    .map(Method::getReturnType);
            Stream.of(fields, parameters, returns)
                    .flatMap(Function.identity())
                    .distinct()
                    .filter(x -> !x.isPrimitive())
                    .filter(x -> !x.getName().startsWith("java"))
                    .filter(x -> !classStack.contains(x))
                    .filter(x -> !result.contains(x))
                    .forEach(classStack::push);
        }

        return result;
    }

    public static Window create(Class<?> aClass) {
        StringBuilder menu = new StringBuilder();
        Map<Integer, Interactable> actions = new HashMap<>();

        Arrays.stream(aClass.getFields())
                .filter(x -> Modifier.isPublic(x.getModifiers()))
                .sorted(Comparator.comparing(Field::getName))
                .forEach(x -> {
                    menu.append(String.format("[%02d] %s%n", actions.size() + 1, x.getName()));
                    actions.put(actions.size() + 1, new InteractionField(x));
                });

        Arrays.stream(aClass.getMethods())
                .filter(x -> !x.getDeclaringClass().getName().startsWith("java"))
                .filter(x -> Modifier.isPublic(x.getModifiers()))
                .filter(x -> !x.getName().equals("main"))
                .sorted(Comparator.comparing(Method::getName)) // TODO: make getter and setter sub-menu? alla tree? ¬¬¬|||---
                .forEach(x -> {
                    menu.append(String.format("(%02d) %s%n", actions.size() + 1, x.getName()));
                    actions.put(actions.size() + 1, new InteractionMethod(x));
                });

        menu.append(String.format("<%02d> %s%n", 0, "Exit"));
        actions.put(0, x -> null);

        return new Window(menu.toString(), actions);
    }

}

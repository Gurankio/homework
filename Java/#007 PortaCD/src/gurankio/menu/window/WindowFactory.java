package gurankio.menu.window;

import gurankio.menu.input.ConsoleInput;
import gurankio.menu.input.ConsoleOutput;
import gurankio.menu.input.GenericFactory;
import gurankio.menu.interaction.Interactable;
import gurankio.menu.interaction.InteractionField;
import gurankio.menu.interaction.InteractionMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: hide + shortcut + menu order
public class WindowFactory {

    private static final List<String> excludedMethods = Arrays.asList("main", "toString", "equals");

    public static List<Class<?>> searchClasses(Class<?> aClass) {
        List<Class<?>> result = new ArrayList<>();
        Stack<Class<?>> classStack = new Stack<>();
        classStack.push(aClass);

        while (!classStack.isEmpty()) {
            Class<?> pClass = classStack.pop();
            result.add(pClass);
            if (pClass.isArray()) {
                classStack.push(pClass.componentType());
                continue;
            }
            Stream<Class<?>> fields = Arrays.stream(pClass.getFields())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .map(Field::getType);
            Stream<Class<?>> parameters = Arrays.stream(pClass.getMethods())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> !excludedMethods.contains(x.getName()))
                    .map(Method::getParameters)
                    .flatMap(Arrays::stream)
                    .map(Parameter::getType);
            Stream<Class<?>> returns = Arrays.stream(pClass.getMethods())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> !excludedMethods.contains(x.getName()))
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

        if (aClass.isArray()) {
            menu.append("Utilities:\n");
            menu.append(String.format("├> (%02d) %s%n", actions.size() + 1, "get"));
            actions.put(actions.size() + 1, instance -> {
                Object[] array = (Object[]) instance;
                ConsoleOutput.println("Getting by index. Array size is " + array.length);
                int i;
                do {
                    i = ConsoleInput.readInt("Index: ");
                    if (i < 0 || i >= array.length) ConsoleOutput.println("Index out of bounds.");
                } while (i < 0 || i >= array.length);
                if (array[i] == null) {
                    ConsoleOutput.println("Value is null. Creating...");
                    array[i] = GenericFactory.create(aClass.componentType());
                }
                return array[i];
            });
            menu.append(String.format("└> (%02d) %s%n", actions.size() + 1, "set"));
            actions.put(actions.size() + 1, instance -> {
                Object[] array = (Object[]) instance;
                ConsoleOutput.println("Setting by index. Array size is " + array.length);
                int i;
                do {
                    i = ConsoleInput.readInt("Index: ");
                    if (i < 0 || i >= array.length) ConsoleOutput.println("Index out of bounds.");
                } while (i < 0 || i >= array.length);
                array[i] = GenericFactory.create(aClass.componentType());
                return array[i];
            });
        } else {
            List<Field> fields = Arrays.stream(aClass.getFields())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList());
            if (fields.size() != 0) menu.append("Fields: \n");
            for (int i = 0; i < fields.size(); i++) {
                menu.append(String.format("%c> (%02d) %s%n", i == fields.size() - 1 ? '└' : '├', actions.size() + 1, fields.get(i).getName()));
                menu.append(String.format("%c> (%02d) %s%n", i == fields.size() - 1 ? 'j' : 'j', actions.size() + 1, fields.get(i).getName()));
                actions.put(actions.size() + 1, new InteractionField(fields.get(i)));
            }

            List<Method> getters = Arrays.stream(aClass.getMethods())
                    .filter(x -> !x.getDeclaringClass().getName().startsWith("java"))
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> !excludedMethods.contains(x.getName()))
                    .filter(x -> x.getName().matches("get.*"))
                    .sorted(Comparator.comparing(Method::getName))
                    .collect(Collectors.toList());
            if (getters.size() != 0) menu.append("Getters: \n");
            for (int i = 0; i < getters.size(); i++) {
                menu.append(String.format("%c> (%02d) %s%n", i == getters.size() - 1 ? 'j' : 'j', actions.size() + 1, getters.get(i).getName()));
                actions.put(actions.size() + 1, new InteractionMethod(getters.get(i)));
            }

            List<Method> setters = Arrays.stream(aClass.getMethods())
                    .filter(x -> !x.getDeclaringClass().getName().startsWith("java"))
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> !excludedMethods.contains(x.getName()))
                    .filter(x -> x.getName().matches("set.*"))
                    .sorted(Comparator.comparing(Method::getName))
                    .collect(Collectors.toList());
            if (setters.size() != 0) menu.append("Setters: \n");
            for (int i = 0; i < setters.size(); i++) {
                menu.append(String.format("%c> (%02d) %s%n", i == setters.size() - 1 ? 'j' : 'h', actions.size() + 1, setters.get(i).getName()));
                actions.put(actions.size() + 1, new InteractionMethod(setters.get(i)));
            }

            List<Method> others = Arrays.stream(aClass.getMethods())
                    .filter(x -> !x.getDeclaringClass().getName().startsWith("java"))
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> !excludedMethods.contains(x.getName()))
                    .filter(x -> !x.getName().matches("get.*|set.*"))
                    .sorted(Comparator.comparing(Method::getName))
                    .collect(Collectors.toList());
            if (others.size() != 0) menu.append("Others: \n");
            for (int i = 0; i < others.size(); i++) {
                menu.append(String.format("%c> (%02d) %s%n", i == others.size() - 1 ? 'j' : 'j', actions.size() + 1, others.get(i).getName()));
                actions.put(actions.size() + 1, new InteractionMethod(others.get(i)));
            }
        }

        menu.append(String.format("<─%02d %s%n", 0, "Exit"));
        actions.put(0, instance -> null);

        return new Window(menu.toString(), actions);
    }

}

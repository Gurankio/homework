package gurankio.menu.window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class ClassSearcher {

    protected static final String excludedJavaPacket = "java.lang";
    protected static final List<String> excludedMethods = Arrays.asList("main", "toString", "equals");

    public static List<Class<?>> run(Class<?> aClass) {
        List<Class<?>> result = new ArrayList<>();
        Stack<Class<?>> classStack = new Stack<>();
        classStack.push(aClass);

        while (!classStack.isEmpty()) {
            Class<?> pClass = classStack.pop();
            result.add(pClass);
            if (pClass.isArray()) {
                classStack.push(pClass.getComponentType());
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
                    .filter(x -> !x.getName().startsWith(excludedJavaPacket)) // TODO: not all java base classes are bad.
                    .filter(x -> !classStack.contains(x))
                    .filter(x -> !result.contains(x))
                    .forEach(classStack::push);
        }

        return result;
    }

}

package gurankio.menu.window;

import gurankio.io.text.TextSerializer;
import gurankio.menu.Menu;
import gurankio.menu.MenuOptions;
import gurankio.menu.window.interactive.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WindowFactory {

    protected static final String excludedJavaPacket = "java.lang";
    protected static final List<String> excludedMethods = Arrays.asList("main", "equals");

    private static List<Class<?>> search(Class<?> entrypoint) {
        List<Class<?>> result = new ArrayList<>();
        Stack<Class<?>> classStack = new Stack<>();
        classStack.push(entrypoint);

        while (!classStack.isEmpty()) {
            Class<?> target = classStack.pop();
            result.add(target);
            if (target.isArray()) {
                classStack.push(target.getComponentType());
                continue;
            }
            Stream<Class<?>> fields = Arrays.stream(target.getFields())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> x.getAnnotation(MenuOptions.Hide.class) == null)
                    .map(Field::getType);
            Stream<Class<?>> parameters = Arrays.stream(target.getMethods())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> x.getAnnotation(MenuOptions.Hide.class) == null)
                    .filter(x -> !excludedMethods.contains(x.getName()))
                    .map(Method::getParameters)
                    .flatMap(Arrays::stream)
                    .map(Parameter::getType);
            Stream<Class<?>> returns = Arrays.stream(target.getMethods())
                    .filter(x -> Modifier.isPublic(x.getModifiers()))
                    .filter(x -> x.getAnnotation(MenuOptions.Hide.class) == null)
                    .filter(x -> !excludedMethods.contains(x.getName()))
                    .map(Method::getReturnType);
            Stream.of(fields, parameters, returns)
                    .flatMap(Function.identity())
                    .distinct()
                    .filter(x -> !x.isPrimitive())
                    .filter(x -> !x.getName().startsWith(excludedJavaPacket))
                    .filter(x -> !classStack.contains(x))
                    .filter(x -> !result.contains(x))
                    .forEach(classStack::push);
        }

        return result;
    }

    public static Map<Class<?>, Window> createAll(Class<?> entrypoint) {
        Map<Class<?>, Window> windows = new ConcurrentHashMap<>();
        // ConsoleMenu.console.debugln("Building menus...");
        Menu.console.incrementIndentation();
        search(entrypoint).parallelStream()
                .forEach(x -> {
                    windows.put(x, create(x));
                    // ConsoleMenu.console.debugln(String.format("Built: %s", x.getSimpleName()));
                });
        Menu.console.decrementIndentation();
        // ConsoleMenu.console.debugln("Done!");
        return windows;
    }

    public static Window create(Class<?> target) {
        List<Interactive> interactives = new ArrayList<>();

        if (target.isArray()) {
          interactives.add(new Interactive() {
              @Override
              public void render(Consumer<String> consumer, Object instance) {
                  consumer.accept("get(index)");
              }

              @Override
              public List<String> getNames() {
                  return Arrays.asList("get");
              }

              @Override
              public Object call(Object instance) {
                  render((s) -> Menu.console.println("Calling '" + s + "'"), instance);
                  Menu.console.incrementIndentation();
                  int index = Menu.console.read("Index: ", Integer.class);
                  Menu.console.decrementIndentation();
                  Object r = ((Object[]) instance)[index];
                  if (r == null) {
                      String confirmInput = Menu.console.read("Did you mean to call "+"set(index, "+ TextSerializer.serialize(target.getComponentType()) +")? [y/N]");
                      if (confirmInput.matches("[yY].*")) {
                          Object object = Menu.console.read("Value: ", target.getComponentType());
                          ((Object[]) instance)[index] = object;
                          return instance;
                      }
                  }
                  return r;
              }
          });
            interactives.add(new Interactive() {
                @Override
                public void render(Consumer<String> consumer, Object instance) {
                    consumer.accept("set(index, "+ TextSerializer.serialize(target.getComponentType()) +")");
                }

                @Override
                public List<String> getNames() {
                    return Arrays.asList("set");
                }

                @Override
                public Object call(Object instance) {
                    render((s) -> Menu.console.println("Calling '" + s + "'"), instance);
                    int index = Menu.console.read("Index: ", Integer.class);
                    Object object = Menu.console.read("Value: ", target.getComponentType());
                    ((Object[]) instance)[index] = object;
                    return instance;
                }
            });
        }

        List<Interactive> fields = Arrays.stream(target.getFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()))
                .filter(x -> x.getAnnotation(MenuOptions.Hide.class) == null)
                .sorted(Comparator.comparing(Field::getName))
                .map(InteractiveField::new)
                .collect(Collectors.toList());
        fields.add(new InteractiveExit());
        interactives.add(new InteractiveWindow("fields", new Window(fields)));

        List<Interactive> getters = Arrays.stream(target.getMethods())
                .filter(m -> !m.getDeclaringClass().getName().startsWith(excludedJavaPacket))
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(x -> x.getAnnotation(MenuOptions.Hide.class) == null)
                .filter(m -> !excludedMethods.contains(m.getName()))
                .filter(m -> m.getName().matches("get.*"))
                .sorted(Comparator.comparing(Method::getName))
                .map(InteractiveGetter::new)
                .collect(Collectors.toList());
        getters.add(new InteractiveExit());
        interactives.add(new InteractiveWindow("getters", new Window(getters)));

        List<Interactive> setters = Arrays.stream(target.getMethods())
                .filter(m -> !m.getDeclaringClass().getName().startsWith(excludedJavaPacket))
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(x -> x.getAnnotation(MenuOptions.Hide.class) == null)
                .filter(m -> !excludedMethods.contains(m.getName()))
                .filter(m -> m.getName().matches("set.*"))
                .sorted(Comparator.comparing(Method::getName))
                .map(InteractiveSetter::new)
                .collect(Collectors.toList());
        setters.add(new InteractiveExit());
        interactives.add(new InteractiveWindow("setters", new Window(setters)));

        List<Interactive> methods = Arrays.stream(target.getMethods())
                .filter(m -> !m.getDeclaringClass().getName().startsWith(excludedJavaPacket))
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(x -> x.getAnnotation(MenuOptions.Hide.class) == null)
                .filter(m -> !excludedMethods.contains(m.getName()))
                .filter(m -> !m.getName().matches("get.*|set.*"))
                .sorted(Comparator.comparing(Method::getName))
                .map(InteractiveMethod::new)
                .collect(Collectors.toList());
        methods.add(new InteractiveExit());
        interactives.add(new InteractiveWindow("methods", new Window(methods)));

        interactives.add(new InteractiveExit());
        return new Window(interactives);
    }

}

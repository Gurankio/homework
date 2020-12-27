package gurankio.menu.io.util;

import gurankio.menu.window.Window;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringPrettify {

    public static Map<Object, String> instanceOverride = new HashMap<>();
    public static Map<Class<?>, Function<Object, String>> classOverride = Map.ofEntries(
            // Rewrite so that it can be generalized with List.class
            new AbstractMap.SimpleEntry<>(ArrayList.class, o -> {
                ArrayList<?> list = (ArrayList<?>) o;
                int length = list.stream().map(e -> toPrettyString(e).length()).reduce(Integer::sum).orElse(0);
                StringBuilder builder = new StringBuilder();
                if (length > 100) builder.append("[..").append(list.size()).append("..]");
                else builder.append(list.stream().map(StringPrettify::toPrettyString).collect(Collectors.joining(", ")));
                return builder.toString();
            }),
            new AbstractMap.SimpleEntry<>(Class.class, o -> ((Class<?>) o).getSimpleName()),
            new AbstractMap.SimpleEntry<>(Field.class, field -> ((Field) field).getName() + (field.getClass().isArray() ? "[]" : "")),
            new AbstractMap.SimpleEntry<>(Parameter.class, parameter -> ((Parameter) parameter).isNamePresent() ? ((Parameter) parameter).getName() : "parameter"),
            new AbstractMap.SimpleEntry<>(Method.class, method ->  ((Method) method).getName() + "(" + Arrays.stream(((Method) method).getParameters()).map(StringPrettify::toPrettyString).collect(Collectors.joining(", ")) + ")"),
            new AbstractMap.SimpleEntry<>(Constructor.class, constructor -> ((Constructor<?>) constructor).getDeclaringClass().getSimpleName() + "(" + Arrays.stream(((Constructor<?>) constructor).getParameters()).map(StringPrettify::toPrettyString).collect(Collectors.joining(", ")) + ")")
    );

    public static String toPrettyString(Object object) {
        if (object == null) return "null";
        if (instanceOverride.containsKey(object)) return instanceOverride.get(object);
        if (classOverride.containsKey(object.getClass())) return classOverride.get(object.getClass()).apply(object);
        if (object.getClass().isArray()) return toPrettyString(Arrays.asList(((Object[]) object)));
        return object.toString();
    }

    /*
    public @interface Override {
        String value();
    }

    @SupportedAnnotationTypes("gurankio.menu.io.util.StringPrettify.Override")
    public static class OverrideProcessor extends AbstractProcessor {

        @java.lang.Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
            annotations.forEach(annotation -> {
                Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            });
            return true;
        }
    }
     */

}

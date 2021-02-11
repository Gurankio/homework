package gurankio.menu.io.util;

import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringPrettify {

    public static Map<Object, String> instanceOverride = new HashMap<>();
    public static Map<Class<? extends Object>, Function<Object, String>> classOverride = new HashMap<>(); 
    
    static {
    	classOverride.put(ArrayList.class, o -> {
            ArrayList<?> list = (ArrayList<?>) o;
            TreeBuilder builder = new TreeBuilder("List");
            for (Object o2 : list) {
                builder.arrow(toPrettyString(o2));
            }
            return builder.endAll().toString();
        });
    	classOverride.put(LocalDateTime.class, o -> ((LocalDateTime) o).toLocalDate().toString());
    	classOverride.put(Class.class, o -> ((Class<?>) o).getSimpleName());
    	classOverride.put(Field.class, field -> ((Field) field).getName() + (field.getClass().isArray() ? "[]" : ""));
    	classOverride.put(Parameter.class, parameter -> ((Parameter) parameter).isNamePresent() ? ((Parameter) parameter).getName() : "parameter");
    	classOverride.put(Method.class, method ->  ((Method) method).getName() + "(" + Arrays.stream(((Method) method).getParameters()).map(StringPrettify::toPrettyString).collect(Collectors.joining(", ")) + ")");
    	classOverride.put(Constructor.class, constructor -> ((Constructor<?>) constructor).getDeclaringClass().getSimpleName() + "(" + Arrays.stream(((Constructor<?>) constructor).getParameters()).map(StringPrettify::toPrettyString).collect(Collectors.joining(", ")) + ")");
    }

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

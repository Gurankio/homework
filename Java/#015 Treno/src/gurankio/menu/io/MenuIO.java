package gurankio.menu.io;

import gurankio.io.text.InvalidInputException;
import gurankio.io.text.TextParser;
import gurankio.io.text.TextSerializer;
import gurankio.util.CharPacks;
import gurankio.util.TreeBuilder;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class MenuIO {

    private Integer indentation;
    private static final HashMap<Class<?>, Function<MenuIO, ?>> overrides = new HashMap<>();

    /**
     * Makes sure to add only matching keys and function.
     *
     * @param target   The class to register.
     * @param function The associated supplier.
     * @param <T>      The registered type.
     */
    public static <T> void registerOverride(Class<T> target, Function<MenuIO, T> function) {
        overrides.put(target, function);
    }

    public MenuIO() {
        indentation = 1;
    }

    public Integer getIndentation() {
        return indentation;
    }
    public void incrementIndentation() {
        indentation++;
    }
    public void decrementIndentation() {
        indentation--;
    }

    public abstract void print(String data);
    public abstract String read(String prompt);

    public void print(Object o) {
        print(TextSerializer.serialize(o));
    }
    public void println() {
        println(" ");
    }
    public void println(Object o) {
        println(TextSerializer.serialize(o));
    }
    public abstract void println(String data);

    public void arrowln(Object o) {
        arrowln(TextSerializer.serialize(o));
    }
    public void arrowln(String data) {
        List<String> lines = data.lines().collect(Collectors.toList());
        for (int i=0; i<lines.size(); i++) {
            if (i == 0) {
                indentation--;
                println(CharPacks.selected.buildArrow(CharPacks.selected.getSpacer()) + lines.get(i));
                indentation++;
            } else println(lines.get(i));
        }
    }

    public Object read(Field field) {
        return read(TextSerializer.serialize(field) + ": ", field.getType());
    }
    public Object read(Parameter parameter) {
        return read(TextSerializer.serialize(parameter) + ": ", parameter.getType());
    }

    @SuppressWarnings("unchecked")
    public <T> T read(String prompt, Class<T> target) {
        if (overrides.containsKey(target)) return (T) overrides.get(target).apply(this);
        if (target.isEnum()) return fromEnum(prompt, target);
        if (TextParser.hasSupplier(target)) {
            do {
                try {
                    return TextParser.parse(target, read(prompt));
                } catch (InvalidInputException exception) {
                    println(exception.getLocalizedMessage());
                }
            } while (true);
        }
        boolean hasConstructor = !(target.isInterface() || Modifier.isAbstract(target.getModifiers())) && target.getConstructors().length > 0;
        if (hasConstructor) return fromConstructor(prompt, target);
        throw new MissingOverrideException(target);
    }

    public String choose(String prompt, List<String> options) {
        if (options.size() == 0) return "";
        if (options.size() == 1) return options.get(0);

        options.sort(Comparator.naturalOrder());

        TreeBuilder builder = new TreeBuilder(prompt);
        options.forEach(builder::arrowCounted);
        println(builder.end().toString());

        // TODO: cool thing with typo and closest match. just has perfect match for now.

        Integer choice;
        do {
            String data = read("Choice: ", String.class);
            try {
                choice = options.contains(data) ? options.indexOf(data) + 1 : TextParser.parse(Integer.class, data);
            } catch (InvalidInputException ignored) {
                choice = -1;
            }
            if (choice < 1 || choice > options.size()) println("Invalid input. Provide a number between 1 and " + options.size());
        } while (choice < 1 || choice > options.size());
        return options.get(choice-1);
    }

    private <T> T fromEnum(String prompt, Class<T> target) {
        return TextParser.parse(target, choose(prompt, Arrays.stream(target.getEnumConstants()).map(Objects::toString).collect(Collectors.toList())));
    }

    private <T> T fromConstructor(String prompt, Class<T> target) {
        // Should not be an issue, as a class can't have constructors of another class?
        @SuppressWarnings("unchecked")
        List<Constructor<T>> constructors = Arrays.stream((Constructor<T>[]) target.getConstructors())
                .sorted(Comparator.comparing(Constructor::getParameterCount))
                .collect(Collectors.toList());

        List<String> names = constructors.stream()
                .map(TextSerializer::serialize)
                .collect(Collectors.toList());

        String chosen = choose(prompt, names);

        Constructor<T> constructor = constructors.stream()
                .filter(c -> TextSerializer.serialize(c).equals(chosen))
                .findFirst()
                .orElseThrow();

        Object[] parameters = Stream.of(constructor.getParameters())
                .map(this::read)
                .toArray();

        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("?");
    }

}

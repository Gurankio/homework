package gurankio.util;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// TODO: add loggers.
public abstract class Menu {

    public static final Map<Class<?>, Double> priority = new HashMap<>();
    static {
        priority.put(char.class, 0.5);
        priority.put(String.class, 0.0);
    }

    private final List<Action<?>> actions;
    private final Set<Class<?>> types;

    public Menu() {
        actions = new LinkedList<>();
        types = new HashSet<>();
        setup();
    }

    protected abstract void setup();

    // Loop

    @SuppressWarnings("unchecked")
    private boolean loop() {
        StringBuilder builder = new StringBuilder();
        Map<Object, Function<Object, Boolean>> options = new HashMap<>();

        for (Action<?> action : actions) {
            String message = action.getMessage().get();
            builder.append(message).append("\n");
            for (Object o : action.getParser().apply(message)) {
                options.put(o, (Function<Object, Boolean>) action.getAction());
            }
        }

        Logger.DIRECT.println();
        Logger.DIRECT.println(builder.toString());

        String in = GenericScanner.next("Inserire qualcosa.");

        Map<Class<?>, Optional<?>> parsed = types.stream().collect(Collectors.toMap(Function.identity(), type -> GenericScanner.parse(in, type)));

        Map.Entry<? extends Class<?>, Function<Object, Boolean>> action = parsed.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent() && options.containsKey(entry.getValue().get()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> options.get(entry.getValue().get())))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByKey(Comparator.comparing(type -> priority.getOrDefault(type, 1.0))))
                .orElse(null);

        Object value;
        if (action == null || (value = parsed.get(action.getKey()).orElse(null)) == null) {
            Logger.WARN.println("Opzione invalida.");
            return true;
        }
        return action.getValue().apply(value);
    }

    public void run() {
        // Intro.

        // Loop.
        while (loop()) {
            GenericScanner.confirm();
        }

        // End?.
    }

    // Building

    private static final Integer TITLE_FONT_SIZE = 3;
    private static final Map<Character, String[]> TITLE_FONT = new HashMap<>();
    static {
        String fontCalvinS = "┌─┐ \n" +
                             "├─┤ \n" +
                             "┴ ┴ \n" +
                             "╔═╗ \n" +
                             "╠═╣ \n" +
                             "╩ ╩ \n" +
                             "┌┐  \n" +
                             "├┴┐ \n" +
                             "└─┘ \n" +
                             "╔╗  \n" +
                             "╠╩╗ \n" +
                             "╚═╝ \n" +
                             "┌─┐ \n" +
                             "│   \n" +
                             "└─┘ \n" +
                             "╔═╗ \n" +
                             "║   \n" +
                             "╚═╝ \n" +
                             "┌┬┐ \n" +
                             " ││ \n" +
                             "─┴┘ \n" +
                             "╔╦╗ \n" +
                             " ║║ \n" +
                             "═╩╝ \n" +
                             "┌─┐ \n" +
                             "├┤  \n" +
                             "└─┘ \n" +
                             "╔═╗ \n" +
                             "║╣  \n" +
                             "╚═╝ \n" +
                             "┌─┐ \n" +
                             "├┤  \n" +
                             "└   \n" +
                             "╔═╗ \n" +
                             "╠╣  \n" +
                             "╚   \n" +
                             "┌─┐ \n" +
                             "│ ┬ \n" +
                             "└─┘ \n" +
                             "╔═╗ \n" +
                             "║ ╦ \n" +
                             "╚═╝ \n" +
                             "┬ ┬ \n" +
                             "├─┤ \n" +
                             "┴ ┴ \n" +
                             "╦ ╦ \n" +
                             "╠═╣ \n" +
                             "╩ ╩ \n" +
                             "┬   \n" +
                             "│   \n" +
                             "┴   \n" +
                             "╦   \n" +
                             "║   \n" +
                             "╩   \n" +
                             " ┬  \n" +
                             " │  \n" +
                             "└┘  \n" +
                             " ╦  \n" +
                             " ║  \n" +
                             "╚╝  \n" +
                             "┬┌─ \n" +
                             "├┴┐ \n" +
                             "┴ ┴ \n" +
                             "╦╔═ \n" +
                             "╠╩╗ \n" +
                             "╩ ╩ \n" +
                             "┬   \n" +
                             "│   \n" +
                             "┴─┘ \n" +
                             "╦   \n" +
                             "║   \n" +
                             "╩═╝ \n" +
                             "┌┬┐ \n" +
                             "│││ \n" +
                             "┴ ┴ \n" +
                             "╔╦╗ \n" +
                             "║║║ \n" +
                             "╩ ╩ \n" +
                             "┌┐┌ \n" +
                             "│││ \n" +
                             "┘└┘ \n" +
                             "╔╗╔ \n" +
                             "║║║ \n" +
                             "╝╚╝ \n" +
                             "┌─┐ \n" +
                             "│ │ \n" +
                             "└─┘ \n" +
                             "╔═╗ \n" +
                             "║ ║ \n" +
                             "╚═╝ \n" +
                             "┌─┐ \n" +
                             "├─┘ \n" +
                             "┴   \n" +
                             "╔═╗ \n" +
                             "╠═╝ \n" +
                             "╩   \n" +
                             "┌─┐ \n" +
                             "│─┼┐\n" +
                             "└─┘└\n" +
                             "╔═╗ \n" +
                             "║═╬╗\n" +
                             "╚═╝╚\n" +
                             "┬─┐ \n" +
                             "├┬┘ \n" +
                             "┴└─ \n" +
                             "╦═╗ \n" +
                             "╠╦╝ \n" +
                             "╩╚═ \n" +
                             "┌─┐ \n" +
                             "└─┐ \n" +
                             "└─┘ \n" +
                             "╔═╗ \n" +
                             "╚═╗ \n" +
                             "╚═╝ \n" +
                             "┌┬┐ \n" +
                             " │  \n" +
                             " ┴  \n" +
                             "╔╦╗ \n" +
                             " ║  \n" +
                             " ╩  \n" +
                             "┬ ┬ \n" +
                             "│ │ \n" +
                             "└─┘ \n" +
                             "╦ ╦ \n" +
                             "║ ║ \n" +
                             "╚═╝ \n" +
                             "┬  ┬\n" +
                             "└┐┌┘\n" +
                             " └┘ \n" +
                             "╦  ╦\n" +
                             "╚╗╔╝\n" +
                             " ╚╝ \n" +
                             "┬ ┬ \n" +
                             "│││ \n" +
                             "└┴┘ \n" +
                             "╦ ╦ \n" +
                             "║║║ \n" +
                             "╚╩╝ \n" +
                             "─┐ ┬\n" +
                             "┌┴┬┘\n" +
                             "┴ └─\n" +
                             "═╗ ╦\n" +
                             "╔╩╦╝\n" +
                             "╩ ╚═\n" +
                             "┬ ┬ \n" +
                             "└┬┘ \n" +
                             " ┴  \n" +
                             "╦ ╦ \n" +
                             "╚╦╝ \n" +
                             " ╩  \n";

        Deque<String> lines = fontCalvinS.lines().collect(Collectors.toCollection(ArrayDeque::new));
        IntStream.range('a', 'z').forEach(c -> {
            String[] lower = new String[TITLE_FONT_SIZE];
            for (int i = 0; i < TITLE_FONT_SIZE; i++) lower[i] = lines.pollFirst();
            TITLE_FONT.put((char) c, lower);
            String[] upper = new String[TITLE_FONT_SIZE];
            for (int i = 0; i < TITLE_FONT_SIZE; i++) upper[i] = lines.pollFirst();
            TITLE_FONT.put(Character.toUpperCase((char) c), upper);
        });
    }

    protected void message(Supplier<String> supplier, String indent) {
        actions.add(new Action<>(() -> Logger.wrap(supplier.get(), indent), null, (message, type) -> Collections.emptySet(), null));
    }

    protected void message(Supplier<String> supplier) {
       message(supplier, " - ");
    }

    protected void message(String message) {
        message(() -> message);
    }

    protected void space() {
        message("");
    }

    protected void title(Supplier<String> supplier) {
        message(() -> {
            String[] empty = new String[TITLE_FONT_SIZE];
            for (int i = 0; i < TITLE_FONT_SIZE; i++) empty[i] = "   ";
            String[][] chars = supplier.get().chars().mapToObj(c -> TITLE_FONT.getOrDefault((char) c, empty)).toArray(String[][]::new);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < TITLE_FONT_SIZE; i++) {
                for (int j = 0; j < chars.length; j++) {
                    builder.append(chars[j][i]);
                }
                builder.append("\n");
            }
            return builder.toString().stripTrailing();
        }, null);
    }

    protected void title(String message) {
        title(() -> message);
    }



    protected <T> void any(Supplier<String> supplier, String indent, Class<T> type, Function<T, Boolean> action) {
        actions.add(new Action<>(() -> Logger.wrap(supplier.get(), indent), type, Menu::parseAny, action));
        types.add(type);
    }

    protected <T> void any(String message, String indent, Class<T> type, Function<T, Boolean> action) {
        any(() -> message, indent, type, action);
    }

    protected <T> void any(Supplier<String> supplier, Class<T> type, Function<T, Boolean> action) {
        any(supplier, " + ", type, action);
    }

    protected <T> void any(String message, Class<T> type, Function<T, Boolean> action) {
        any(() -> message, type, action);
    }



    protected <T> void first(Supplier<String> supplier, String indent, Class<T> type, Function<T, Boolean> action) {
        actions.add(new Action<>(() -> Logger.wrap(supplier.get(), indent), type, Menu::parseFirst, action));
        types.add(type);
    }

    protected <T> void first(String message, String indent, Class<T> type, Function<T, Boolean> action) {
        first(() -> message, indent, type, action);
    }

    protected <T> void first(Supplier<String> supplier, Class<T> type, Function<T, Boolean> action) {
        first(supplier, " - ", type, action);
    }

    protected <T> void first(String message, Class<T> type, Function<T, Boolean> action) {
        first(() -> message, type, action);
    }



    protected <T> void sub(Supplier<String> supplier, String indent, Class<T> type, Menu menu) {
        first(supplier, indent, type, in -> {
            menu.run();
            return true;
        });
    }

    protected <T> void sub(String supplier, String indent, Class<T> type, Menu menu) {
        sub(() -> supplier, indent, type, menu);
    }

    protected <T> void sub(Supplier<String> supplier, Class<T> type, Menu menu) {
        sub(supplier, " * ", type, menu);
    }

    protected <T> void sub(String message, Class<T> type, Menu menu) {
        sub(() -> message, type, menu);
    }

    // Parsing

    private static final Pattern pattern = Pattern.compile(".*\\[(.+)].*");

    private static <T> Stream<Optional<T>> parse(String message, Class<T> type) {
        return message.lines()
                .map(pattern::matcher)
                .filter(Matcher::matches)
                .map(matcher -> GenericScanner.parse(matcher.group(1), type));
    }

    private static <T> Set<T> parseAny(String message, Class<T> type) {
        return parse(message, type)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private static <T> Set<T> parseFirst(String message, Class<T> type) {
        return parse(message, type)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .limit(1)
                .collect(Collectors.toSet());
    }

    // Data Structure

    private static class Action<T> {

        private final Supplier<String> message;
        private final Class<T> type;
        private final BiFunction<String, Class<T>, Set<T>> parser;
        private final Function<T, Boolean> action;

        public Action(Supplier<String> message, Class<T> type, BiFunction<String, Class<T>, Set<T>> parser, Function<T, Boolean> action) {
            this.message = message;
            this.parser = parser;
            this.type = type;
            this.action = action;
        }

        public Supplier<String> getMessage() {
            return message;
        }

        public Class<T> getType() {
            return type;
        }

        public Function<String, Set<T>> getParser() {
            return s -> parser.apply(s, type);
        }

        public Function<T, Boolean> getAction() {
            return action;
        }
    }

}

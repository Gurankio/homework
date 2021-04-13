package gurankio.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// TODO: String as an input type should enable partial match and regexes.
public abstract class DynamicMenu {

    public static final Map<Class<?>, Double> priority = new HashMap<>();
    static {
        priority.put(char.class, 0.0);
        priority.put(String.class, 0.5);
    }

    private final List<Action<?>> actions;

    public DynamicMenu() {
        actions = new LinkedList<>();
    }

    protected abstract void setup();

    protected void hello() {
        println("Questo menu utilizza le parentesi quadre per indicare gli elementi interagibili.");
        println("Scrivi il loro contenuto per muoverti nel menu!");
        println("Nel caso una riga abbia più di una coppia di quadre, scrivi il contenuto di tutte.");
        println("Inoltre quando una riga comincia con '...' significa che il menu sta aspettando una conferma.");
        println("Premi INVIO per continuare.");
        GenericScanner.confirm();
    }

    protected void bye() {

    }

    // Loop

    @SuppressWarnings("unchecked")
    private boolean loop() {
        StringBuilder builder = new StringBuilder();
        Map<Object, Function<Object, Boolean>> options = new HashMap<>();

        for (Action<?> action : actions) {
            String message = action.getMessage().get();
            builder.append(message).append("\n");
            for (Object o : parse(message, action.getType())) {
                options.put(o, (Function<Object, Boolean>) action.getAction());
            }
        }

        println();
        println(Logger.DIRECT, builder);

        String in = GenericScanner.next("Inserire la sua scelta.");

        Map<Class<?>, Optional<?>> parsed = actions.parallelStream()
                .map(Action::getType)
                .filter(Objects::nonNull) // filter out messages
                .distinct()
                .collect(Collectors.toMap(Function.identity(), type -> GenericScanner.parse(in, type)));

        Map.Entry<? extends Class<?>, Function<Object, Boolean>> action = parsed.entrySet().stream()
                .filter(entry -> entry.getValue().isPresent() && options.containsKey(entry.getValue().get()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> options.get(entry.getValue().get())))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByKey(Comparator.comparing(type -> priority.getOrDefault(type, 1.0))))
                .orElse(null);

        Object value;
        if (action == null || (value = parsed.get(action.getKey()).orElse(null)) == null) {
            warnln("Opzione invalida.");
            return true;
        }
        return action.getValue().apply(value);
    }

    public void run() {
        setup();

        // Hello.
        hello();

        // Loop.
        while (loop()) GenericScanner.confirm();

        // Bye.
        bye();
    }

    // Building the Loop!

    protected void message(Supplier<String> supplier, String indent) {
        actions.add(new Action<>(() -> Logger.wrap(supplier.get(), indent), null,  null));
    }

    protected void message(Supplier<String> supplier) {
       message(supplier, "   ");
    }

    protected void message(String message) {
        message(() -> message);
    }



    protected <T> void option(Supplier<String> supplier, String indent, Class<T> type, Function<T, Boolean> action) {
        actions.add(new Action<>(() -> Logger.wrap(supplier.get(), indent), type, action));
    }

    protected <T> void options(Supplier<String> supplier, String indent, Class<T> type, Function<T, Boolean> action) {
       option(supplier, indent, type, action);
    }

    protected <T> void option(String message, String indent, Class<T> type, Function<T, Boolean> action) {
        option(() -> message, indent, type, action);
    }

    protected <T> void options(String message, String indent, Class<T> type, Function<T, Boolean> action) {
        option(message, indent, type, action);
    }

    protected <T> void option(Supplier<String> supplier, Class<T> type, Function<T, Boolean> action) {
        option(supplier, " - ", type, action);
    }

    protected <T> void options(Supplier<String> supplier, Class<T> type, Function<T, Boolean> action) {
        option(supplier, " + ", type, action);
    }

    protected <T> void option(String message, Class<T> type, Function<T, Boolean> action) {
        option(() -> message, type, action);
    }

    protected <T> void options(String message, Class<T> type, Function<T, Boolean> action) {
        options(() -> message, type, action);
    }



    protected <T> void sub(Supplier<String> supplier, String indent, Class<T> type, Function<T, DynamicMenu> menu) {
        option(supplier, indent, type, in -> {
            menu.apply(in).run();
            return true;
        });
    }

    protected <T> void sub(String supplier, String indent, Class<T> type, Function<T, DynamicMenu> menu) {
        sub(() -> supplier, indent, type, menu);
    }

    protected <T> void sub(Supplier<String> supplier, Class<T> type, Function<T, DynamicMenu> menu) {
        sub(supplier, " * ", type, menu);
    }

    protected <T> void sub(String message, Class<T> type, Function<T, DynamicMenu> menu) {
        sub(() -> message, type, menu);
    }

    // Building Shorthands

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
                "┬ \n" +
                "│ \n" +
                "┴ \n" +
                "╦ \n" +
                "║ \n" +
                "╩ \n" +
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
                "┌─┐  \n" +
                "│─┼┐ \n" +
                "└─┘└ \n" +
                "╔═╗  \n" +
                "║═╬╗ \n" +
                "╚═╝╚ \n" +
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
                "┬  ┬ \n" +
                "└┐┌┘ \n" +
                " └┘  \n" +
                "╦  ╦ \n" +
                "╚╗╔╝ \n" +
                " ╚╝  \n" +
                "┬ ┬ \n" +
                "│││ \n" +
                "└┴┘ \n" +
                "╦ ╦ \n" +
                "║║║ \n" +
                "╚╩╝ \n" +
                "─┐ ┬ \n" +
                "┌┴┬┘ \n" +
                "┴ └─ \n" +
                "═╗ ╦ \n" +
                "╔╩╦╝ \n" +
                "╩ ╚═ \n" +
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
        }, "   ");
    }

    protected void title(String message) {
        title(() -> message);
    }

    protected void space() {
        message("");
    }

    protected void close(String message, Class<?> type) {
        option(message, type, (x) -> false);
    }

    // Static Building

    public static <T> T choose(String s, List<String> strings, Class<String> type, List<T> classes) {
        new DynamicMenu() {
            @Override
            protected void hello() {
            }

            @Override
            protected void setup() {
                message(s);
            }
        };
        return null;
    }

    // Parsing

    private static final Pattern pattern = Pattern.compile("\\[(.+?)]");

    private static <T> Set<T> parse(String message, Class<T> type) {
        return message.lines()
                .map(pattern::matcher)
                .map(Matcher::results)
                .map(results -> results
                        .map(matchResult -> matchResult.group(1))
                        .reduce(String::concat)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(data -> GenericScanner.parse(data, type))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    // Data Structure

    private static class Action<T> {

        private final Supplier<String> message;
        private final Class<T> type;
        private final Function<T, Boolean> action;

        public Action(Supplier<String> message, Class<T> type, Function<T, Boolean> action) {
            this.message = message;
            this.type = type;
            this.action = action;
        }

        public Supplier<String> getMessage() {
            return message;
        }

        public Class<T> getType() {
            return type;
        }

        public Function<T, Boolean> getAction() {
            return action;
        }
    }

    // Utility

    protected void println(Logger logger) {
        logger.println();
    }
    protected void println(Logger logger, String s) {
        logger.println(s);
    }
    protected void println(Logger logger, Object o) {
        logger.println(o);
    }

    protected void println() {
        println(Logger.INFO);
    }
    protected void println(String s) {
        println(Logger.INFO, s);
    }
    protected void println(Object o) {
        println(Logger.INFO, o);
    }

    protected void warnln(String s) {
        println(Logger.WARN, s);
    }
    protected void warnln(Object o) {
        println(Logger.WARN, o);
    }

    protected void listln(String header, Stream<String> elements) {
       println(header);
       elements.forEach(element -> println(Logger.DIRECT, Logger.wrap(element, " + ")));
    }

}

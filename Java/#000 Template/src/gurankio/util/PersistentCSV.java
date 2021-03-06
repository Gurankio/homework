package gurankio.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface PersistentCSV {

    void serialize(Stack<String> tokens);
    boolean parse(Stack<String> tokens);

    ExecutorService POOL = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    static Future<Boolean> asave(File file, Stream<? extends PersistentCSV> elements) {
        return POOL.submit(() -> save(file, elements));
    }

    static boolean save(File file, Stream<? extends PersistentCSV> elements) {
        try (PrintStream writer = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            elements.parallel()
                    .map(element -> {
                        Stack<String> stack = new Stack<>();
                        stack.add(element.getClass().getName());
                        try {
                            element.serialize(stack);
                        } catch (RuntimeException e) {
                            Logger.DEBUG.exception(e);
                            return null;
                        }
                        return stack.stream().map(token -> token.replaceAll(",", "\\,")).collect(Collectors.joining(", "));
                    })
                    .filter(Objects::nonNull)
                    .forEach(writer::println);
            return true;
        } catch (IOException e) {
            Logger.DEBUG.exception(e);
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    static Stream<? extends PersistentCSV> load(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines().collect(Collectors.toList()).parallelStream() // This loads the entire file into memory. Nice! \s
                    .map(line -> {
                        List<String> tokens = Arrays.stream(line.split("(?=[^\\\\]), "))
                                .map(token -> token.replaceAll("\\\\,", ","))
                                .collect(Collectors.toList());
                        try {
                            PersistentCSV persistent = ((Class<? extends PersistentCSV>) Class.forName(tokens.get(0))).getConstructor().newInstance();
                            return persistent.parse(tokens.stream().skip(1).collect(Collectors.toCollection(Stack::new))) ? persistent : null;
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                            Logger.DEBUG.exception(e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull);
        } catch (IOException e) {
            Logger.DEBUG.exception(e);
            return Stream.empty();
        }
    }
}

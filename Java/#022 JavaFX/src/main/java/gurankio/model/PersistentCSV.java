package gurankio.model;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface PersistentCSV {

    void serialize(Stack<String> tokens);
    boolean parse(Stack<String> tokens);

    static boolean save(File file, Stream<? extends PersistentCSV> elements) {
        try (PrintStream writer = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            elements.map(element -> {
                        Stack<String> stack = new Stack<>();
                        stack.add(element.getClass().getName());
                        try {
                            element.serialize(stack);
                        } catch (RuntimeException e) {
                            // e.printStackTrace();
                            return null;
                        }
                        return stack.stream().map(token -> token.replaceAll(",", "\\,")).collect(Collectors.joining(", "));
                    })
                    .filter(Objects::nonNull)
                    .forEach(writer::println);
            return true;
        } catch (IOException e) {
            // e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    static Stream<? extends PersistentCSV> load(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines().sequential().collect(Collectors.toList()).stream() // This loads the entire file into memory. Nice! \s
                    .map(line -> {
                        List<String> tokens = Arrays.stream(line.split("(?=[^\\\\]), "))
                                .map(token -> token.replaceAll("\\\\,", ","))
                                .collect(Collectors.toList());
                        try {
                            PersistentCSV persistent = ((Class<? extends PersistentCSV>) Class.forName(tokens.get(0))).getConstructor().newInstance();
                            List<String> temp = tokens.stream().skip(1).collect(Collectors.toList());
                            Collections.reverse(temp);
                            return persistent.parse(temp.stream().collect(Collectors.toCollection(Stack::new))) ? persistent : null;
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                            // e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull);
        } catch (IOException e) {
            // e.printStackTrace();
            return Stream.empty();
        }
    }
}

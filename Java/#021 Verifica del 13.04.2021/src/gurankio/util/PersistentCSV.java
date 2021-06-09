package gurankio.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface PersistentCSV {

    void serialize(Queue<String> tokens);
    boolean parse(Queue<String> tokens);

    /**
     * Serializes and writes a stream of objects in CSV format.
     * Serializing in handed off to {@link PersistentCSV#serialize(Queue)}.
     * {@code null} can be handled during save, but will be interpreted as an error on load. TODO: nulls on load should not be treated as an error.
     * Serialization is executed in parallel, so be wary of synchronization when writing a serialization function.
     *
     * @param file the file to write
     * @param stream the stream of elements to serialize and save
     * @throws IOException as thrown by {@link FileOutputStream#FileOutputStream(File)}
     */
    static void save(File file, Stream<? extends PersistentCSV> stream) throws IOException {
        try (PrintStream writer = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            writer.println(stream
                    .parallel()
                    .map(element -> {
                        Queue<String> queue = new LinkedList<>();
                        queue.add(element.getClass().getName());
                        element.serialize(queue);
                        return queue;
                    })
                    .map(queue -> queue.stream()
                            .map(token -> token == null ? "null" : token) // TODO: mhh.
                            .map(token -> token.strip().replaceAll(",", "\\,"))
                            .collect(Collectors.joining(", ")))
                    .collect(Collectors.joining("\n"))
            );
        }
    }

    static void save(String path, Stream<? extends PersistentCSV> stream) throws IOException {
        save(new File(path), stream);
    }

    /**
     * Utility asynchronous save method to keep the main thread responsive.
     *
     * @param file the file to write
     * @param stream the stream of elements to serialize and save
     * @return the success of the operation TODO: should not be a boolean?
     */
    static Future<Boolean> asave(File file, Stream<? extends PersistentCSV> stream) {
        return ForkJoinPool.commonPool().submit(() -> {
            try {
                save(file, stream);
                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }

    ///

    Pattern SPLIT_REGEX = Pattern.compile("(?<!(?<!\\\\)\\\\),\\s*");
    Pattern CLEAN_REGEX = Pattern.compile("\\\\,");

    /**
     * Loads and parses the path given line by line.
     * Parsing is handed off to {@link PersistentCSV#parse(Queue)}.
     * Un-parsable lines are returned as {@code null}.
     * Un-parsable lines might be caused by negative return value of {@link PersistentCSV#parse(Queue)} or by a failure to follow the Java Beans Standard by the implementing class.
     *
     * @param path the path to load
     * @return a parallel stream of parsed objects
     * @throws IOException as thrown by {@link Files#lines(Path, Charset)}
     */
    @SuppressWarnings("unchecked")
    static Stream<? extends PersistentCSV> load(Path path) throws IOException {
        return Files.lines(path)
                .map(line ->
                        SPLIT_REGEX.splitAsStream(line)
                        .map(token -> CLEAN_REGEX.matcher(token).replaceAll(","))
                        .collect(Collectors.toCollection(ArrayDeque::new)))
                .map(queue -> {
                    try {
                        PersistentCSV persistent = ((Class<? extends PersistentCSV>) Class.forName(queue.remove())).getConstructor().newInstance();
                        return persistent.parse(queue) ? persistent : null;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                        // Does not follow the JavaBeans standard.
                        return null;
                    }
                });
    }

    static Stream<? extends PersistentCSV> load(String uri) throws IOException {
        return load(Path.of(uri));
    }

    /**
     * Exception-less {@link PersistentCSV#load(Path) load} utility which returns an empty stream if an {@code IOException} would have been raised by {@link PersistentCSV#load(Path)}
     * Might be useful for static initialization.
     *
     * @param path the path to load
     * @return a parallel stream of parsed objects, or an empty stream if an {@code IOException} is raised.
     */
    static Stream<? extends PersistentCSV> loadOrEmpty(Path path) {
        try {
            return load(path);
        } catch (IOException e) {
            return Stream.empty();
        }
    }

    static Stream<? extends PersistentCSV> loadOrEmpty(String uri) {
        return loadOrEmpty(Path.of(uri));
    }

    /**
     * Calls {@link PersistentCSV#loadOrEmpty(Path)} and returns only objects that may be assigned to the given class.
     * By needs of the stream interface, errors (see {@link PersistentCSV#load(Path)} for error handling while parsing), are discarded.
     *
     * @param path the path to load
     * @param target the class for whom to check assignability
     * @param <T> the type for whom to check assignability
     * @return a parallel, filtered and casted stream to the given type, might be empty.
     */
    static <T extends PersistentCSV> Stream<T> loadClass(Path path, Class<T> target) {
        return loadOrEmpty(path)
                .filter(Objects::nonNull)
                .filter(persistent -> target.isAssignableFrom(persistent.getClass()))
                .map(target::cast);
    }

    static <T extends PersistentCSV> Stream<T> loadClass(String uri, Class<T> target) {
        return loadClass(Path.of(uri), target);
    }

}

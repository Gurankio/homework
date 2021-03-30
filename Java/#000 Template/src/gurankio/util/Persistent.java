package gurankio.util;

import java.beans.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  XML encoded Persistence Utility.
 *  TODO: Javadoc.
 */
@SuppressWarnings("unchecked")
public abstract class Persistent implements Serializable {

    // List of subclasses, needed for persistence delegates.
    private static final CopyOnWriteArraySet<Class<?>> subClasses = new CopyOnWriteArraySet<>();

    // Bean

    private final transient File file;

    public Persistent() {
        this.file = file(UUID.randomUUID().toString(), getClass());
        subClasses.add(getClass());
    }

    public Persistent(File file) {
        this.file = file;
        subClasses.add(getClass());

        if (load())
            Logger.DEBUG.println("Successfully loaded a '" + getClass().getSimpleName() + "' instance from '" + getFile()  + "'.");
    }

    public File getFile() {
        return file;
    }

    // File Input

    public boolean save() {
        blocked.add(file.getName()); // Disable loading for this file.
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            addPersistenceDelegates(encoder, getClass());
            encoder.writeObject(this);
            return true;
        } catch (FileNotFoundException e) {
            Logger.DEBUG.exception(e);
            return false;
        } finally {
            blocked.remove(file.getName());
        }
    }

    // Async saves thread pool.
    // Parallelism is not meant to speed up save operations but to keep the main thread responsive.
    private static final ExecutorService POOL = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    public Future<Boolean> asave() {
        blocked.add(file.getName());
        return POOL.submit(this::save);
    }

    // File Output

    private static final Set<String> blocked = new ConcurrentSkipListSet<>(); // Blocked files buffer.

    public boolean load() {
        if (blocked.contains(file.getName())) return false; // Avoids loops. TODO: wait for availability?

        Persistent object;

        blocked.add(file.getName()); // Disable loading for this file.
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            object = (Persistent) decoder.readObject();
        } catch (FileNotFoundException e) {
            Logger.DEBUG.println("Tried to load nonexistent file '" + file +"'.");
            return false;
        } finally {
            blocked.remove(file.getName());
        }

        if (!object.getClass().equals(getClass())) {
            Logger.DEBUG.println("Class mismatch: " + getClass().getSimpleName() + " tried to load a " + object.getClass().getSimpleName() + " file.");
            return false;
        }

        try {
            BeanInfo info = Introspector.getBeanInfo(getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                if (pd.getWriteMethod() == null || pd.getReadMethod() == null) continue;
                pd.getWriteMethod().invoke(this, pd.getReadMethod().invoke(object));
            }
            return true;
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            Logger.DEBUG.exception(e);
            return false;
        }
    }

    // Nomenclature Utilities

    // Custom folder support should not help in any scenario...
    public static String folder(Class<? extends Persistent> type) {
        StringBuilder path = new StringBuilder();
        Class<?> current = type;
        do path.insert(0, current.getSimpleName() + "/"); // TODO: change nomenclature
        while (!(current = current.getSuperclass()).equals(Object.class));
        File folder = new File(path.toString().toLowerCase(Locale.ROOT));
        if (folder.mkdirs()) Logger.DEBUG.println("Creating '" + folder + "' folder.");
        return folder.getAbsolutePath();
    }

    /// Static Extension Map

    private static final File EXTENSIONS_FILE = new File("persistent/extensions.xml");
    private static Map<String, Class<? extends Persistent>> extensions;

    static {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(EXTENSIONS_FILE)))) {
            extensions = (Map<String, Class<? extends Persistent>>) decoder.readObject(); // Not unchecked as it's only populated by extension().
        } catch (Exception e) {
            extensions = new ConcurrentHashMap<>();
        }
    }

    private static String extension(Class<? extends Persistent> type) {
        long capitals = type.getSimpleName().chars().filter(Character::isUpperCase).count();
        String extension = capitals < 3 ?
                type.getSimpleName().chars().map(Character::toLowerCase).filter(c -> c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u').distinct().mapToObj(c -> String.format("%c", c)).collect(Collectors.joining()) :
                type.getSimpleName().chars().filter(Character::isUpperCase).mapToObj(c -> String.format("%c", c)).collect(Collectors.joining());

        extensions.put(extension, type);
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(EXTENSIONS_FILE)))) {
            encoder.writeObject(extensions);
        } catch (FileNotFoundException e) {
            Logger.DEBUG.exception(e);
        }

        return extension;
    }

    private static File file(String filename, Class<? extends Persistent> type) {
        return new File(folder(type) + "/" + filename + "." + extension(type));
    }

    // Encoders Utility

    private void addPersistenceDelegates(Encoder encoder, Class<? extends Persistent> caller) {
        // Set all Persistent subclasses, but the caller, which gets just custom constructor, to File only constructor.
        encoder.setPersistenceDelegate(caller, new DefaultPersistenceDelegate(new String[]{ "file" }));
        subClasses.stream()
                .filter(persistent -> !persistent.equals(caller))
                .forEach(persistent -> encoder.setPersistenceDelegate(persistent, new PersistenceDelegate() {
                    @Override
                    protected Expression instantiate(Object target, Encoder encoder) {
                        ((Persistent) target).asave(); // asave might not be the smartest idea...
                        return new Expression(target, persistent, "new", new Object[]{ ((Persistent) target).getFile() });
                    }
                }));
        // Set File to a relative URI.
        encoder.setPersistenceDelegate(File.class, new PersistenceDelegate() {
            @Override
            protected Expression instantiate(Object target, Encoder encoder) {
                Path other = Path.of(((File) target).toURI());
                Path persistentParent = Path.of((new File(folder(Persistent.class)).getParentFile()).toURI());
                return new Expression(target, File.class, "new",
                        new Object[]{ persistentParent.relativize(other).toString() });
            }
        });
        // Utility LocalDateTime.
        encoder.setPersistenceDelegate(LocalDateTime.class, new PersistenceDelegate() {
            @Override
            protected Expression instantiate(Object target, Encoder encoder) {
                LocalDateTime other = (LocalDateTime) target;
                return new Expression(other, LocalDateTime.class, "of",
                        new Object[]{
                                other.getYear(),
                                other.getMonthValue(),
                                other.getDayOfMonth(),
                                other.getHour(),
                                other.getMinute(),
                                other.getSecond(),
                                other.getNano(),
                        });
            }
        });
    }

    // Static File Input

    private static Persistent of(File file) {
        try {
            Class<? extends Persistent> type = extensions.entrySet().stream().filter(entry -> file.getName().endsWith("." + entry.getKey())).findFirst().orElseThrow().getValue();
            return type.getConstructor(File.class).newInstance(file);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            Logger.DEBUG.exception(e);
            return null;
        }
    }

    private static Stream<File> deepListFiles(File folder) {
        File[] files = folder.listFiles();
        if (files == null) return Stream.empty();
        else return Arrays.stream(files)
                .flatMap(file -> {
                    if (file.isDirectory()) return deepListFiles(file);
                    else return Stream.of(file);
                });
    }

    // Note: returns a parallel stream as load operations are done in parallel.
    public static <T extends Persistent> Stream<T> stream(Class<T> type) {
        return deepListFiles(new File(folder(type)))
                .parallel()
                .map(Persistent::of)
                .filter(Objects::nonNull)
                .filter(persistent -> type.isAssignableFrom(persistent.getClass()))
                .map(persistent -> (T) persistent); // Not unchecked as: 1) we check above if it is assignable and 2) is guaranteed by the folder organization.
    }
}

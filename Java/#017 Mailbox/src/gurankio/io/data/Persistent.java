package gurankio.io.data;

import java.beans.*;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// Sottoclassi necessitano di un costruttore con un solo parametro file.
@SuppressWarnings("all")
public abstract class Persistent implements Serializable {

    // Object

    // Won't get saved to file.
    private File file;

    public Persistent() {
        this.file = of(UUID.randomUUID().toString(), getClass());
    }

    public Persistent(File file) {
        this.file = file;
        load();

        // if (load()) System.out.println(getClass().getSimpleName() + " instance loaded.");
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    // Instance IO

    public boolean save() {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(getFile())))) {
            addPersistenceDelegates(encoder);

            encoder.writeObject(getClass());
            Arrays.stream(getClass().getMethods())
                    .filter(method -> !method.getDeclaringClass().equals(Object.class) && method.getName().matches("set.*"))
                    .filter(method -> method.getParameterCount() == 1)
                    .filter(method -> !method.getName().matches("setFile"))
                    .sorted(Comparator.comparing(Method::getName))
                    .map(method -> method.getName().replace("set", "get"))
                    .map(name -> {
                        try {
                            return getClass().getMethod(name);
                        } catch (NoSuchMethodException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .map(method -> {
                        try {
                            Object o = method.invoke(this);
                            if (o.getClass().isArray()) {
                                Object[] a = new Object[1 + ((Collection<?>) o).size()];
                                List<Class<?>> classes = new ArrayList<>();
                                for (int i = 0; i < Array.getLength(o); i++) classes.add(Array.get(o, i).getClass());
                                a[0] = new CollectionData(o.getClass(), classes);
                                System.arraycopy(o, 0, a, 1, Array.getLength(o));
                                return a;
                            }
                            else if (o instanceof Collection<?>) {
                                Object[] a = new Object[1 + ((Collection<?>) o).size()];
                                a[0] = new CollectionData(o.getClass(), ((Collection<?>) o).stream().map(Object::getClass).collect(Collectors.toList()));
                                System.arraycopy(((Collection<?>) o).toArray(), 0, a, 1, ((Collection<?>) o).size());
                                return a;
                            }
                            else return new Object[]{o};
                        } catch (IllegalAccessException | InvocationTargetException ignored) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .flatMap(Arrays::stream)
                    .forEach(o -> {
                        if (o instanceof Persistent) {
                            ((Persistent) o).save();
                            o = Paths.get(getFolder().getAbsolutePath()).relativize(Paths.get(((Persistent) o).getFile().getAbsolutePath())).toString();
                        }
                        encoder.writeObject(o);
                    });
            return true;
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean load() {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(getFile())))) {
            Class<?> target = (Class<?>) decoder.readObject();
            if (!getClass().equals(target)) {
                // System.err.println("Something has gone wrong...");
                return false;
            }

            Arrays.stream(target.getMethods())
                    .filter(method -> !method.getDeclaringClass().equals(Object.class) && method.getName().matches("set.*"))
                    .filter(method -> method.getParameterCount() == 1)
                    .filter(method -> !method.getName().matches("setFile"))
                    .sorted(Comparator.comparing(Method::getName))
                    .forEach(method -> {
                        try {
                            Object o = decoder.readObject();
                            if (o instanceof CollectionData) {
                                List<Class<?>> contents = ((CollectionData) o).getContents();
                                o = ((CollectionData) o).getCollection().getConstructor().newInstance();
                                for (Class<?> content : contents) {
                                    if (Persistent.class.isAssignableFrom(content)) {
                                        ((Collection<Persistent>) o).add(((Persistent) content.getConstructor(File.class).newInstance(new File(getFolder().getAbsolutePath() + "/" + decoder.readObject()))));
                                    } else {
                                        ((Collection<Object>) o).add(decoder.readObject());
                                    }
                                }
                            }
                            method.invoke(this, o);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                            // e.printStackTrace();
                        }
                    });
            return true;
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean delete() {
        return getFile().delete();
    }

    // Static Utility

    public static <T extends Persistent> List<T> list(Class<T> target) {
        File[] files = getFolder().listFiles((dir, name) -> name.endsWith(getExtension(target)));
        assert files != null;

        List<T> list = new ArrayList<>(files.length);
        for (File file : files) {
            try {
                list.add(target.getConstructor(File.class).newInstance(file));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static File of(String name, Class<? extends Persistent> target) {
        return new File(getFolder().getAbsolutePath() + "/" + name + "." + getExtension(target));
    }

    // Internal

    private static File getFolder() {
        File folder = new File("persistent/");
        if (folder.mkdirs()) System.out.println("Creating persistent folder...");
        return folder;
    }

    private static String getExtension(Class<? extends Persistent> target) {
        long capitals = target.getSimpleName().chars().filter(Character::isUpperCase).count();
        return capitals < 3 ?
                target.getSimpleName().chars().map(Character::toLowerCase).filter(c -> c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u').distinct().mapToObj(c -> String.format("%c", c)).collect(Collectors.joining()) :
                target.getSimpleName().chars().filter(Character::isUpperCase).mapToObj(c -> String.format("%c", c)).collect(Collectors.joining());
    }

    private static void addPersistenceDelegates(XMLEncoder encoder) {
        PersistenceDelegate localDateTimeDelegate = new PersistenceDelegate() {
            @Override
            protected Expression instantiate(Object target,
                                             Encoder encoder) {
                LocalDateTime other = (LocalDateTime) target;
                return new Expression(other, LocalDateTime.class, "of",
                        new Object[] {
                                other.getYear(),
                                other.getMonthValue(),
                                other.getDayOfMonth(),
                                other.getHour(),
                                other.getMinute(),
                                other.getSecond(),
                                other.getNano(),
                        });
            }
        };
        encoder.setPersistenceDelegate(LocalDateTime.class, localDateTimeDelegate);
    }

    // Internal collection class.

    public static class CollectionData {

        private Class<?> collection;
        private List<Class<?>> contents;

        public CollectionData() {
            this.collection = null;
            this.contents = new ArrayList<>();
        }

        public CollectionData(Class<?> target, List<Class<?>> contents) {
            this.collection = target;
            this.contents = contents;
        }

        public Class<?> getCollection() {
            return collection;
        }

        public void setCollection(Class<?> collection) {
            this.collection = collection;
        }

        public List<Class<?>> getContents() {
            return contents;
        }

        public void setContents(List<Class<?>> contents) {
            this.contents = contents;
        }
    }
}

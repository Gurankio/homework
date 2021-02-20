package gurankio.io.file;

import gurankio.io.text.TextParser;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextFile implements FileInterface {

    private class Node {

        private Object object;
        private List<Node> children;
        private boolean visited;

        public Node(Object object) {
            this.object = object;
            this.children = new ArrayList<>();
            this.visited = false;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public List<Object> getAllObjects() {
            List<Object> objects = new ArrayList<>();
            objects.add(getObject());
            getChildren().forEach(node -> objects.addAll(node.getAllObjects()));
            return objects;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void addChildren(Node child) {
            this.children.add(child);
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }
    }

    private boolean isPrimitiveOrBoxed(Class<?> target) {
        if (target.isArray()) return isPrimitiveOrBoxed(target.getComponentType());
        if (target.isPrimitive()) return true;
        if (target.equals(Byte.class)) return true;
        if (target.equals(Short.class)) return true;
        if (target.equals(Integer.class)) return true;
        if (target.equals(Long.class)) return true;
        if (target.equals(Float.class)) return true;
        if (target.equals(Double.class)) return true;
        if (target.equals(Boolean.class)) return true;
        if (target.equals(Character.class)) return true;
        if (target.equals(String.class)) return true;
        return false;
    }

    private Class<?> getFinalComponentType(Class<?> target) {
        if (target.isArray()) return getFinalComponentType(target.getComponentType());
        else return target;
    }

    @Override
    public void save(Object object, File file) throws InvalidExtensionException {
        Class<?> target = object.getClass();
        Node root = new Node(object);
        Stack<Node> current = new Stack<>();
        current.push(root);

        while (current.size() != 0) {
            Node node = current.pop();
            for (Field field : node.getObject().getClass().getDeclaredFields()) {
                if (Modifier.isTransient(target.getModifiers())) {
                    continue;
                }

                if (!isPrimitiveOrBoxed(field.getType())) {
                    field.setAccessible(true);
                    try {
                        if (field.getType().isArray()) {
                            Object[] ns = (Object[]) field.get(node.getObject());
                            for (Object n : ns) {
                                Node next = new Node(n);
                                node.addChildren(next);
                                current.add(next);
                            }
                        } else {
                            Node next = new Node(field.get(node.getObject()));
                            node.addChildren(next);
                            current.add(next);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Object o : root.getAllObjects()) {
                writer.write(o.getClass().getSimpleName() + "; ");

                for (Field field : o.getClass().getDeclaredFields()) {
                    if (Modifier.isTransient(target.getModifiers())) {
                        continue;
                    }

                    field.setAccessible(true);
                    Object n = field.get(o);

                    if (isPrimitiveOrBoxed(field.getType())) {
                        if (field.getType().isArray()) {
                            switch (field.getType().getComponentType().getSimpleName()) {
                                case "byte":
                                    writer.write(Arrays.toString((byte[]) field.get(o)) + "; ");
                                    break;
                                case "short":
                                    writer.write(Arrays.toString((short[]) field.get(o)) + "; ");
                                    break;
                                case "int":
                                    writer.write(Arrays.toString((int[]) field.get(o)) + "; ");
                                    break;
                                case "long":
                                    writer.write(Arrays.toString((long[]) field.get(o)) + "; ");
                                    break;
                                case "float":
                                    writer.write(Arrays.toString((float[]) field.get(o)) + "; ");
                                    break;
                                case "double":
                                    writer.write(Arrays.toString((double[]) field.get(o)) + "; ");
                                    break;
                                case "boolean":
                                    writer.write(Arrays.toString((boolean[]) field.get(o)) + "; ");
                                    break;
                                case "String":
                                    writer.write(Stream.of((String[]) field.get(o)).map(e -> "\"" + e + "\"").collect(Collectors.toList()).toString());
                                    break;
                            }
                        } else {
                            writer.write(n.toString() + "; ");
                        }
                    } else {
                        if (n instanceof Collection) {
                            writer.write(((Collection<?>) n).size() + "; ");
                        }

                        if (field.getType().isArray()) {
                            Object[] array = (Object[]) field.get(o);
                            writer.write(array.length + "; ");
                        }
                    }
                }

                writer.write(System.lineSeparator());
            }

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private <T> T next(Class<T> target, BufferedReader reader) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        @SuppressWarnings("unchecked")
        T t = (T) Arrays.stream(target.getConstructors()).min(Comparator.comparing(Constructor::getParameterCount)).orElseThrow().newInstance();
        String[] tokens = reader.readLine().split("; ");
        Field[] fields = t.getClass().getDeclaredFields();
        for (int i=0; i<fields.length; i++) {
            if (Modifier.isTransient(target.getModifiers())) {
                continue;
            }

            fields[i].setAccessible(true);
            if (fields[i].getType().isArray()) {
                if (TextParser.hasSupplier(getFinalComponentType(fields[i].getType()))) {
                    fields[i].set(t, TextParser.parse(fields[i].getType(), tokens[i+1]));
                } else {
                    int size = TextParser.parse(int.class, tokens[i+1]);
                    Object a =  Array.newInstance(fields[i].getType().getComponentType(), size);
                    for (int j=0; j<size; j++) Array.set(a, j, next(fields[i].getType().getComponentType(), reader));
                    fields[i].set(t, a);
                }
            } else {
                if (TextParser.hasSupplier(fields[i].getType())) fields[i].set(t, TextParser.parse(fields[i].getType(), tokens[i+1]));
                else fields[i].set(t, next(fields[i].getType(), reader));
            }
        }
        return t;
    }

    @Override
    public <T> T load(File file, Class<T> target) throws InvalidExtensionException, FileNotFoundException {
        if (!file.exists()) throw new FileNotFoundException();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return next(target, reader);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package gurankio.io.file;

import gurankio.io.text.TextParser;
import gurankio.io.text.TextSerializer;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class TextFile implements FileInterface {

    private static class Node {

        private Object object;
        private final List<Node> children;

        public Node(Object object) {
            this.object = object;
            this.children = new ArrayList<>();
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
    }

    @Override
    public boolean save(Object object, File file) throws InvalidExtensionException {
        Node root = new Node(object);
        Stack<Node> current = new Stack<>();
        current.push(root);

        try {
            while (current.size() != 0) {
                Node node = current.pop();
                List<Method> methods = Arrays.stream(node.getObject().getClass().getMethods())
                        .parallel()
                        .filter(method -> method.getName().matches("get.*"))
                        .filter(method -> method.getParameterCount() == 0)
                        .sorted(Comparator.comparing(Method::getName))
                        .collect(Collectors.toList());

                for (Method method : methods) {
                    Class<?> methodReturn = method.getReturnType();
                    if (!TextSerializer.hasClassSupplier(methodReturn)) {
                        if (methodReturn.isArray()) {
                            for (Object e : (Object[]) method.invoke(node.getObject())) {
                                Node next = new Node(e);
                                node.addChildren(next);
                                current.add(next);
                            }
                        } else if (method.invoke(node.getObject()) instanceof Iterable<?>) {
                            for (Object e : (Iterable<?>) method.invoke(node.getObject())) {
                                Node next = new Node(e);
                                node.addChildren(next);
                                current.add(next);
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Object o : root.getAllObjects()) {
                writer.write(o.getClass().getName() + "; ");

                List<Method> methods = Arrays.stream(o.getClass().getMethods())
                        .parallel()
                        .filter(method -> !method.getDeclaringClass().getSimpleName().equals("Object") && method.getName().matches("get.*"))
                        .filter(method -> method.getParameterCount() == 0)
                        .sorted(Comparator.comparing(Method::getName))
                        .collect(Collectors.toList());

                for (Method method : methods) {
                    Class<?> methodReturn = method.getReturnType();
                    if (TextSerializer.hasClassSupplier(methodReturn)) {
                        writer.write(TextSerializer.serialize(method.invoke(o)) + "; ");
                    } else {
                        if (methodReturn.isArray()) {
                            Object[] a = (Object[]) method.invoke(o);
                            writer.write("(" + a.length + "," + a.getClass().getName() + "); ");
                        } else if (method.invoke(o) instanceof Iterable<?>) {
                            long size = 0;
                            for (Object e : (Iterable<?>) method.invoke(o)) size++;
                            writer.write("(" + size + "," + method.invoke(o).getClass().getName() + "); ");
                        }
                    }
                }

                writer.write(System.lineSeparator());
            }

        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Object next(BufferedReader reader) throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String[] tokens = Arrays.stream(reader.readLine().split(";")).map(String::strip).toArray(String[]::new);
        int newToken = 0;

        Object out = Arrays.stream(Class.forName(tokens[0]).getConstructors())
                .min(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow()
                .newInstance();

        List<Method> methods = Arrays.stream(out.getClass().getMethods())
                .parallel()
                .filter(method -> !method.getDeclaringClass().getSimpleName().equals("Object") && method.getName().matches("set.*"))
                .filter(method -> method.getParameterCount() == 1)
                .sorted(Comparator.comparing(Method::getName))
                .collect(Collectors.toList());

        for (Method method : methods) {
            newToken++;
            Class<?> parameter = method.getParameters()[0].getType();

            if (tokens[newToken].matches("\\(.*\\)")) {
                String[] collectionData = tokens[newToken].replaceAll("\\(", "").replaceAll("\\)", "").split(",");
                int collectionSize = Integer.parseInt(collectionData[0]);
                Class<?> collectionClass = Class.forName(collectionData[1]);

                if (collectionClass.isArray()) {
                    Object array = Array.newInstance(parameter.getComponentType(), collectionSize);
                    for (int i = 0; i < collectionSize; i++) Array.set(array, i, next(reader));
                    method.invoke(out, array);
                } else {
                    // getConstructors return a ?[] not a T[]
                    @SuppressWarnings("unchecked")
                    Collection<Object> collection = (Collection<Object>) Arrays.stream(collectionClass.getConstructors())
                            .min(Comparator.comparing(Constructor::getParameterCount))
                            .orElseThrow()
                            .newInstance();

                    for (int i = 0; i < collectionSize; i++) collection.add(next(reader));
                    method.invoke(out, collection);
                }

                continue;
            }

            if (TextParser.hasSupplier(parameter)) method.invoke(out, TextParser.parse(parameter, tokens[newToken]));
            else method.invoke(out, next(reader));
        }

        return out;
    }

    @Override
    public <T> T load(File file, Class<T> target) throws InvalidExtensionException, FileNotFoundException {
        if (!file.exists()) throw new FileNotFoundException();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Object o = next(reader);
            if (target.isInstance(o)) return (T) o;
            else return null;
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}

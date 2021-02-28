package gurankio.io.file;

import java.io.*;

/**
 * IO su file binari tramite ObjectInputStream ed ObjectOutputStream.
 *
 * @author Jacopo Del Granchio
 */
public class BinaryFile implements FileInterface {

    private static final String EXTENSION = ".bin";

    @Override
    public boolean save(Object object, File file) throws InvalidExtensionException {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file))) {
            writer.writeObject(object);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(File file, Class<T> target) throws InvalidExtensionException, FileNotFoundException {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
            Object o = reader.readObject();
            if (target.isInstance(o)) return (T) o;
            return null;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}

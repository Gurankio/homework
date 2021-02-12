package gurankio.io.file;

import gurankio.io.exception.InvalidExtensionException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class XmlFile implements FileInterface {

    private static final String EXTENSION = ".xml";

    /**
     * Saves an object serialized in XML into the given file.
     *
     * @param object The object to save.
     * @param file   The file to write to. If it doesn't exists it is going to be created.
     */
    @Override
    public void save(Object object, File file) throws InvalidExtensionException {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);
        try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            e.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an instance of the given class from the given XML file.
     *
     * @param file   The file to read.
     * @param target The class to load.
     * @param <T>    The type of the class.
     * @return       The parsed object.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(File file, Class<T> target) throws InvalidExtensionException, FileNotFoundException {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);
        try (XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            Object o = d.readObject();
            if (target.isInstance(o)) return (T) o;
            return null;
        }
    }
}

package gurankio.io.file;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class XmlFile implements FileInterface {


    /**
     * Saves an object serialized in XML into the given file.
     *
     * @param object The object to save.
     * @param file   The file to write to. If it doesn't exists it is going to be created.
     */
    @Override
    public void save(Object object, File file) {
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
    public <T> T load(File file, Class<T> target) throws FileNotFoundException {
        try (XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            Object o = d.readObject();
            if (target.isInstance(o)) return (T) o;
            return null;
        }
    }
}

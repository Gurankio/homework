package gurankio.io.file;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class XmlFile implements FileInterface {

    private static final String EXTENSION = ".xml";

    @Override
    public boolean save(Object object, File file) throws FileNotFoundException, InvalidExtensionException {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);
        try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            e.writeObject(object);
            return true;
        } catch (SecurityException exception) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(File file, Class<T> target) throws FileNotFoundException, InvalidExtensionException {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);
        try (XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            Object o = d.readObject();
            if (target.isInstance(o)) return (T) o;
            return null;
        } catch (SecurityException exception) {
            return null;
        }
    }
}

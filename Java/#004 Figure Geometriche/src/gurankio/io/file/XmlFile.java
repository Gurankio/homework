package gurankio.io.file;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Interfaccia sulla codifica XML.
 *
 * @author Jacopo Del Granchio
 */
public class XmlFile implements FileInterface {

    private static final String EXTENSION = ".xml";

    @Override
    public boolean save(Object object, File file) throws FileNotFoundException, InvalidExtensionException {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);

        // Speciale sintassi del try..catch che chiude in automatico la scrittura su file.
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            encoder.writeObject(object);
            return true;
        } catch (SecurityException exception) {
            return false;
        }
    }

    // L'IDE ci avverte che il cast del oggetto a tipo T potrebbe generare un'eccezione. Possiamo Ignorarla dato che la catturiamo nel catch.
    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(File file, Class<T> target) throws FileNotFoundException, InvalidExtensionException {
        if (!file.getName().endsWith(EXTENSION)) throw new InvalidExtensionException(file, EXTENSION);

        // Speciale sintassi del try..catch che chiude in automatico la lettura da file.
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            Object o = decoder.readObject();
            return (T) o;
        } catch (SecurityException | ArrayIndexOutOfBoundsException | ClassCastException exception) {
            return null;
        }
    }
}

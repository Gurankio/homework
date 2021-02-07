package gurankio.io.file;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class XmlFile implements FileInterface {

    @Override
    public void save(Object object, File file) {
        try (XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))) {
            e.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(File file, Class<T> target) {
        try (XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            Object o = d.readObject();
            if (target.isInstance(o)) return (T) o;
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

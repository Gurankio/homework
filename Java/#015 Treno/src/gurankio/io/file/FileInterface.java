package gurankio.io.file;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileInterface {

    void save(Object object, File file) throws InvalidExtensionException;
    <T> T load(File file, Class<T> target) throws InvalidExtensionException, FileNotFoundException;

}

package gurankio.io.file;

import gurankio.io.exception.InvalidExtensionException;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileInterface {

    void save(Object object, File file) throws InvalidExtensionException;
    <T> T load(File file, Class<T> target) throws InvalidExtensionException, FileNotFoundException;

}

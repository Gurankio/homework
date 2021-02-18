package gurankio.io.file;

import java.io.File;

public interface FileInterface {

    void save(Object object, File file);
    <T> T load(File file, Class<T> target);

}

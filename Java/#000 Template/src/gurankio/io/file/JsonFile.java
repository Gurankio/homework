package gurankio.io.file;

import java.io.File;

public class JsonFile implements FileInterface {

    @Override
    public void save(Object object, File file) {

    }

    @Override
    public <T> T load(File file, Class<T> target) {
        return null;
    }
}

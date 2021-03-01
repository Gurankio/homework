package gurankio.io.data;

import gurankio.io.file.FileInterface;
import gurankio.io.file.TextFile;
import gurankio.menu.MenuOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

public interface Persistent extends Serializable {

    @MenuOptions.Hide
    FileInterface fileInterface = new TextFile();

    @MenuOptions.Hide
    default String getExtension() {
        return ".txt";
    }

    @MenuOptions.Hide
    default String getFolder() {
        File folder = new File("data");
        folder.mkdirs();
        return folder.getAbsolutePath() + "/";
    }

    @MenuOptions.Hide
    default String getUUID() {
        return UUID.nameUUIDFromBytes(ByteBuffer.allocate(4).putInt(hashCode()).array()).toString();
    }

    default File getFile() {
        return new File(getFolder() + getUUID() + getExtension());
    }

    @MenuOptions.Hide
    default boolean save() {
        try {
            return fileInterface.save(this, getFile());
        } catch (FileNotFoundException e) {
           return false;
        }
    }

    @MenuOptions.Hide
    default Persistent load() {
        try {
            return fileInterface.load(getFile(), getClass());
        } catch (FileNotFoundException e) {
            return this;
        }
    }

}

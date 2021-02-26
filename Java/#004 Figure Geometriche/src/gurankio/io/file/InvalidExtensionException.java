package gurankio.io.file;

import java.io.File;

/**
 * TODO
 */
public class InvalidExtensionException extends RuntimeException {

    public InvalidExtensionException(File file, String expected) {
        super("Invalid file name extension: " + file.getName() + ". \"" + expected + "\" excpected.");
    }
}

package gurankio.io.exception;

import java.io.File;

public class InvalidExtensionException extends RuntimeException {

    public InvalidExtensionException(File file, String expected) {
        super("Invalid file name extension: " + file.getName() + ". \"" + expected + "\" excpected.");
    }
}

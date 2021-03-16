package gurankio.menu.io;

public class MissingOverrideException extends RuntimeException {

    public MissingOverrideException(Class<?> target) {
        super("Missing override for '" + target + "'.");
    }
}

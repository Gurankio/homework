package gurankio.io.text;

public class SupplierTypeMismatchException extends RuntimeException {

    public SupplierTypeMismatchException(Class<?> key) {
        super("The supplier with key '" + key + "' does not match its return type.");
    }

}


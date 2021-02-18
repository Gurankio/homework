package gurankio.io.exception;

public class MissingSupplierException extends RuntimeException {

    public MissingSupplierException(Class<?> target) {
        super("Missing supplier for '" + target + "'.");
    }
}

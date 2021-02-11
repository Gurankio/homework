package gurankio;

public class UnknownSolutionException extends RuntimeException {

    public UnknownSolutionException() {
        super("Soluzione indeterminata.");
    }
}

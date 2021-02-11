package gurankio;

public class IllegalDegreeException extends RuntimeException {

    public IllegalDegreeException() {
        super("'a' non puo essere 0.");
    }
}

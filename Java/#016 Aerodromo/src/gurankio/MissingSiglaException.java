package gurankio;

public class MissingSiglaException extends RuntimeException {

    public MissingSiglaException() {
        super("Sigla inesistente.");
    }
}

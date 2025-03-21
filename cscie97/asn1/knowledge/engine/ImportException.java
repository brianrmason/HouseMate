package cscie97.asn1.knowledge.engine;

public class ImportException extends Exception {

    public ImportException() {
        super();
    }

    public ImportException (String message) {
        super(message);
    }

    public ImportException (String message, Throwable cause) {
        super(message, cause);
    }
}

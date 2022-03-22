package Exceptions;

/**
 * Thrown when schema initialisation failed.
 */
public class DBInitException extends Exception {

    /**
     * Thrown when schema initialisation failed.
     */
    public DBInitException() {
        super("Failed to initialize database");
    }

    /**
     * Thrown when schema initialisation failed.
     *
     * @param message Error message
     */
    public DBInitException(String message) {
        super(message);
    }
}

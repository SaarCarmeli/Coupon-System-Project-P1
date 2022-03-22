package Exceptions;

/**
 * Thrown when table initialisation failed.
 */
public class DBException extends Exception {
    /**
     * Thrown when table initialisation failed.
     */
    public DBException() {
        super("Failed to perform DB operation");
    }

    /**
     * Thrown when table initialisation failed.
     *
     * @param message Error message
     */
    public DBException(String message) {
        super(message);
    }
}

package Exceptions;

public class DBInitException extends Exception {
    public DBInitException() {
        super("Failed to initialize database");
    }

    public DBInitException(String message) {
        super(message);
    }
}

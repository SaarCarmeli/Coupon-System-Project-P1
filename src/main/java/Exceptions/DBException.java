package Exceptions;

public class DBException extends Exception {
    public DBException() {
        super("Failed to perform DB operation");
    }

    public DBException(final String msg) {
        super(msg);
    }
}

package Exceptions;

public class DBException extends Exception {
    public DBException(final String msg) {
        super("Failed to perform DB operation");
    }
}

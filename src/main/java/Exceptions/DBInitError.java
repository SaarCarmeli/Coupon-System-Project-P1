package Exceptions;

public class DBInitError extends Error {
    public DBInitError() {
        super("Failed to initialize database");
    }
}

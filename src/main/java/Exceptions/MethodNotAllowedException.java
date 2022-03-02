package Exceptions;

public class MethodNotAllowedException extends Exception {
    public MethodNotAllowedException() {
    }

    public MethodNotAllowedException(String message) {
        super(message);
    }
}

package Exceptions;

public class NoCouponsLeftException extends Exception{
    public NoCouponsLeftException() {
        super("No more of this coupon is left! Purchase failed!");
    }

    public NoCouponsLeftException(String message) {
        super(message);
    }
}

package Exceptions;

public class CouponExpiredException extends Exception{
    public CouponExpiredException() {
        super("Coupon can not be purchased as it is expired!");
    }

    public CouponExpiredException(String message) {
        super(message);
    }
}

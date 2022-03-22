package Exceptions;

/**
 * Thrown when coupon is expired. Can not purchase Coupon if it is expired.
 */
public class CouponExpiredException extends Exception {
    /**
     * Thrown when coupon is expired. Can not purchase Coupon if it is expired.
     */
    public CouponExpiredException() {
        super("Coupon can not be purchased as it is expired!");
    }

    /**
     * Thrown when coupon is expired. Can not purchase Coupon if it is expired.
     *
     * @param message Error message
     */
    public CouponExpiredException(String message) {
        super(message);
    }
}

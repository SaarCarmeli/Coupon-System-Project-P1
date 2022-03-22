package Exceptions;

/**
 * Thrown when no coupons left (Coupon amount = 0). Can not purchase Coupon if no Coupons in inventory.
 */
public class NoCouponsLeftException extends Exception {

    /**
     * Thrown when no coupons left (Coupon amount = 0). Can not purchase Coupon if no Coupons in inventory.
     */
    public NoCouponsLeftException() {
        super("No more of this coupon is left! Purchase failed!");
    }

    /**
     * Thrown when no coupons left (Coupon amount = 0). Can not purchase Coupon if no Coupons in inventory.
     *
     * @param message Error message
     */
    public NoCouponsLeftException(String message) {
        super(message);
    }
}

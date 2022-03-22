package Facades;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.CouponExpiredException;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Exceptions.NoCouponsLeftException;

import java.util.ArrayList;

public interface CustomerFacadeDAO {
    void purchaseCoupon(Coupon coupon) throws EntityCrudException, EntityAlreadyExistException, NoCouponsLeftException, CouponExpiredException;
    Coupon readCouponById(int couponId) throws EntityCrudException;
    ArrayList<Coupon> readAllCustomerCoupons() throws EntityCrudException;
    ArrayList<Coupon> readCustomerCoupons(Category category) throws EntityCrudException;
    ArrayList<Coupon> readCustomerCoupons(double maxPrice) throws EntityCrudException;
    Customer getCustomerDetails() throws EntityCrudException;
}

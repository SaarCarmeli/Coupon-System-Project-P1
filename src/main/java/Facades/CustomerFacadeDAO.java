package Facades;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Exceptions.NoCouponsLeftException;

import java.util.ArrayList;

public interface CustomerFacadeDAO {
    void purchaseCoupon(Coupon coupon) throws EntityCrudException, EntityAlreadyExistException, NoCouponsLeftException;
    Coupon getCouponById(int couponId) throws EntityCrudException;
    ArrayList<Coupon> getCustomerCoupons() throws EntityCrudException;
    ArrayList<Coupon> getCustomerCoupons(Category category) throws EntityCrudException;
    ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws EntityCrudException;
    Customer getCustomerDetails() throws EntityCrudException;
}

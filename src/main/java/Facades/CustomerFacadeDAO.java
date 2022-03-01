package Facades;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.EntityCrudException;

import java.util.ArrayList;

public interface CustomerFacadeDAO {
    void purchaseCoupon(Coupon coupon);
    ArrayList<Coupon> getCustomerCoupons() throws EntityCrudException;
    ArrayList<Coupon> getCustomerCoupons(Category category);
    ArrayList<Coupon> getCustomerCoupons(double maxPrice);
    Customer getCustomerDetails();
}

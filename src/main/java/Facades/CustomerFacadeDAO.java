package Facades;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;

import java.util.ArrayList;

public interface CustomerFacadeDAO {
    void purchaseCoupon(Coupon coupon);
    ArrayList<Coupon> getCustomerCoupons();
    ArrayList<Coupon> getCustomerCoupons(Category category);
    ArrayList<Coupon> getCustomerCoupons(double maxPrice);
    Customer getCustomerDetails();
}

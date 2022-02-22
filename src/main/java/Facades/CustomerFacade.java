package Facades;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;

import java.util.ArrayList;

public class CustomerFacade implements CustomerFacadeDAO {
    private final int customerId;

    public CustomerFacade(int customerId) {
        this.customerId = customerId;
    }


    // todo implement methods:
    @Override
    public void purchaseCoupon(Coupon coupon) {

    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons() {
        return null;
    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons(Category category) {
        return null;
    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {
        return null;
    }

    @Override
    public Customer getCustomerDetails() {
        return null;
    }
}

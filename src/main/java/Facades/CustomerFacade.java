package Facades;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.EntityCrudException;

import java.util.ArrayList;

public class CustomerFacade implements CustomerFacadeDAO {
    private final int customerId;
    private final Customer customer; // todo: is necessary?

    public CustomerFacade(int customerId) throws EntityCrudException {
        this.customerId = customerId;
        this.customer = CustomerDBDAO.getInstance().readCustomer(customerId); // todo: is necessary?
    }


    // todo implement methods:
    @Override
    public void purchaseCoupon(Coupon coupon) {

    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons() throws EntityCrudException {
        return (ArrayList<Coupon>) CustomerDBDAO.getInstance().readCouponsByCustomerId(customerId);
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
        return this.customer;
    }
}

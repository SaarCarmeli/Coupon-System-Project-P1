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

    public CustomerFacade(int customerId) throws EntityCrudException {
        this.customerId = customerId;
    }

    // todo implement methods:
    @Override
    public void purchaseCoupon(Coupon coupon) {

    }

    @Override
    public ArrayList<Coupon> getCustomerCoupons() throws EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().readCouponsByCustomerId(customerId);
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
    public Customer getCustomerDetails() throws EntityCrudException {
        CustomerDBDAO customerDBDAO = CustomerDBDAO.getInstance();
        return customerDBDAO.readCustomer(customerId);
    }
}

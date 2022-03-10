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

    /**
     * Constructs a CustomerFacade that implements the methods a customer client is allowed to use. Accessed via log-in from the LoginManager.
     * @param customerId The logged customer's ID number, received from login method in LoginManager
     */
    public CustomerFacade(int customerId) {
        this.customerId = customerId;
    }

    // todo implement methods:
    @Override
    public void purchaseCoupon(Coupon coupon) {

    }

    /**
     * Returns a list of all unexpired coupons that the logged customer owns.
     *
     * @return List of all the customer's coupons
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public ArrayList<Coupon> getCustomerCoupons() throws EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().readCouponsByCustomerId(this.customerId);
    }

    /**
     * Returns a list of the unexpired coupons that the logged customer owns filtered by category.
     *
     * @param category Filtering category
     * @return List of the customer's coupons that meet the criteria
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public ArrayList<Coupon> getCustomerCoupons(Category category) throws EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().readCouponsByCustomerIdAndCategory(this.customerId, String.valueOf(category));
    }

    /**
     * Returns a list of the unexpired coupons that the logged customer owns filtered by maximum price.
     *
     * @param maxPrice Filtering maximum price
     * @return List of the customer's coupons that meet the criteria
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().readCouponsByCustomerIdAndMaxPrice(this.customerId, maxPrice);
    }

    /**
     * Returns the details of the customer that logged in including:
     * Customer ID number, First name, Last name and Email
     *
     * @return Customer details from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Customer getCustomerDetails() throws EntityCrudException {
        CustomerDBDAO customerDBDAO = CustomerDBDAO.getInstance();
        return customerDBDAO.readCustomer(customerId);
    }
}

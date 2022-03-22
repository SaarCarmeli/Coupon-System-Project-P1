package Facades;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerFacade implements CustomerFacadeDAO {
    private final int customerId;

    /**
     * Constructs a CustomerFacade that implements the methods a customer client is allowed to use. Accessed via log-in from the LoginManager.
     *
     * @param customerId The logged customer's ID number, received from login method in LoginManager
     */
    public CustomerFacade(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Adds a new Coupon purchase for a Customer to the database.
     * Checks that the Customer hasn't already purchased this Coupon (can not buy the same coupon twice).
     * Checks that there are coupons available for purchase (coupon amount is not 0).
     * Lowers the amount of coupons by 1 after purchase is complete.
     *
     * @param coupon Coupon being purchased
     * @throws EntityCrudException         Thrown if Count in MySQL was unsuccessful
     * @throws EntityAlreadyExistException Thrown if the customer already purchased this coupon
     * @throws NoCouponsLeftException      Thrown if there are no coupons left to purchase
     */
    @Override
    public void purchaseCoupon(Coupon coupon) throws EntityCrudException, EntityAlreadyExistException, NoCouponsLeftException, CouponExpiredException {
        int newCouponAmount;
        if (CouponDBDAO.getInstance().isPurchaseExistByIds(coupon.getId(), customerId)) {
            throw new EntityAlreadyExistException(EntityType.PURCHASE);
        }
        if (coupon.getAmount() <= 0) {
            throw new NoCouponsLeftException();
        }
        if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))){
            throw new CouponExpiredException();
        }
        CouponDBDAO.getInstance().addCouponPurchase(customerId, coupon.getId());
        newCouponAmount = coupon.getAmount() - 1;
        coupon.setAmount(newCouponAmount);
        CouponDBDAO.getInstance().updateCoupon(coupon);
        System.out.println("Coupon Amount -1");
    }

    /**
     * Returns a single Coupon by Coupon ID number.
     *
     * @param couponId Coupon ID number
     * @return One Coupon
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Coupon readCouponById(int couponId) throws EntityCrudException {
        return CouponDBDAO.getInstance().readCoupon(couponId);
    }

    /**
     * Returns a list of all unexpired coupons that the logged customer owns.
     *
     * @return List of all the customer's coupons
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public ArrayList<Coupon> readAllCustomerCoupons() throws EntityCrudException {
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
    public ArrayList<Coupon> readCustomerCoupons(Category category) throws EntityCrudException {
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
    public ArrayList<Coupon> readCustomerCoupons(double maxPrice) throws EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().readCouponsByCustomerIdAndMaxPrice(this.customerId, maxPrice);
    }

    /**
     * Returns the details of the customer that logged in including:
     * Customer ID number, First name, Last name and Email,
     * Not including password
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

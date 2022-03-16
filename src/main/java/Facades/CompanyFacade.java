package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DBDAO.CompanyDBDAO;
import DBDAO.CouponDBDAO;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.util.ArrayList;

public class CompanyFacade implements CompanyFacadeDAO {
    private final int companyId;

    /**
     * Constructs a CompanyFacade that implements the methods a company client is allowed to use. Accessed via log-in from the LoginManager.
     *
     * @param companyId The logged company's ID number, received from login method in LoginManager
     */
    public CompanyFacade(int companyId) {
        this.companyId = companyId;
    }

    /**
     * Creates Coupon record in the database. Can not create a Coupon with the same title as another Coupon of the same Company.
     *
     * @param coupon Coupon instance to create the record by
     * @throws EntityAlreadyExistException Thrown if a Coupon with the same title by the same company exists in the database
     * @throws EntityCrudException         Thrown if Create in MySQL was unsuccessful
     */
    @Override
    public void addCoupon(Coupon coupon) throws EntityAlreadyExistException, EntityCrudException {
        if (CouponDBDAO.getInstance().isCouponExistByCompanyId(this.companyId, coupon.getTitle())) {
            throw new EntityAlreadyExistException(EntityType.COUPON);
        }
        CouponDBDAO.getInstance().createCoupon(coupon);
    }

    /**
     * Updates Coupon record in the database. Can not update Coupon ID number, Company ID number or Start date.
     *
     * @param coupon Coupon instance to update the record by
     * @throws EntityCrudException Thrown if Update in MySQL was unsuccessful
     */
    @Override
    public void updateCoupon(Coupon coupon) throws EntityCrudException {
        CouponDBDAO.getInstance().updateCoupon(coupon);
    }

    /**
     * Deletes Coupon record and purchase history from the database.
     *
     * @param couponId Coupon's ID number
     * @throws EntityCrudException Thrown if Delete in MySQL was unsuccessful
     */
    @Override
    public void deleteCoupon(int couponId) throws EntityCrudException {
        CouponDBDAO.getInstance().deleteCoupon(couponId);
    }

    /**
     * Returns a single Company-issued Coupon by Coupon ID number.
     *
     * @param couponId Coupon ID number
     * @return One Company-issued Coupon
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Coupon getCouponById(int couponId) throws EntityCrudException {
        return CouponDBDAO.getInstance().readCoupon(couponId);
    }

    /**
     * Returns a list of all unexpired coupons that the logged company issued.
     *
     * @return List of all the company's coupons
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public ArrayList<Coupon> getCompanyCoupons() throws EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().readCouponsByCompanyId(this.companyId);
    }

    /**
     * Returns a list of the unexpired coupons that the logged company issued filtered by category.
     *
     * @param category Filtering category
     * @return List of the company's coupons that meet the criteria
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public ArrayList<Coupon> getCompanyCoupons(Category category) throws EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().readCouponsByCompanyIdAndCategory(this.companyId, String.valueOf(category));
    }

    /**
     * Returns a list of the unexpired coupons that the logged company issued filtered by maximum price.
     *
     * @param maxPrice Filtering maximum price
     * @return List of the company's coupons that meet the criteria
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().readCouponsByCompanyIdAndMaxPrice(this.companyId, maxPrice);
    }

    /**
     * Returns the details of the company that logged in, including:
     * Company ID number, Name and Email
     *
     * @return Company details from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Company getCompanyDetails() throws EntityCrudException {
        CompanyDBDAO companyDBDAO = CompanyDBDAO.getInstance();
        return companyDBDAO.readCompany(companyId);
    }
}

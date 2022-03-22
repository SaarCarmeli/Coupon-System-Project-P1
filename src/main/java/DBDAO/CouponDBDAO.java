package DBDAO;


import Beans.Coupon;
import DB.DBManager;
import DB.DBTools;
import DB.ObjectExtractionUtil;
import Exceptions.CrudOperation;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class containing all Create-Read-Update-Delete methods for "coupons" and "customer_to_coupon" tables.
 */
public class CouponDBDAO implements CouponDAO {
    private static CouponDBDAO instance = null;

    /**
     * Private constructor for CouponDBDAO.
     */
    private CouponDBDAO() {
    }

    /**
     * Static method for retrieving and/or initiating an instance of CouponDBDAO.
     *
     * @return CouponDBDAO instance
     */
    public static CouponDBDAO getInstance() {
        if (instance == null) {
            synchronized (CouponDBDAO.class) {
                if (instance == null) {
                    instance = new CouponDBDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Create Coupon record in MySQL database.
     *
     * @param coupon Coupon instance to create record by
     * @throws EntityCrudException Thrown if Create in MySQL was unsuccessful
     */
    @Override
    public void createCoupon(Coupon coupon) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getAmount());
        params.put(3, coupon.getPrice());
        params.put(4, coupon.getCategory());
        params.put(5, coupon.getTitle());
        params.put(6, coupon.getDescription());
        params.put(7, coupon.getImage());
        params.put(8, coupon.getStartDate());
        params.put(9, coupon.getEndDate());
        try {
            System.out.println("Created Coupon: " + DBTools.runQuery(DBManager.CREATE_COUPON, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE);
        }
    }

    /**
     * Create Coupon purchase record in MySQL database.
     *
     * @param customerId ID number of buying Customer
     * @param couponId   ID number of bought Coupon
     * @throws EntityCrudException Thrown if Create in MySQL was unsuccessful
     */
    @Override
    public void addCouponPurchase(Integer customerId, Integer couponId) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);
        try {
            System.out.println("Added Coupon purchase: " + DBTools.runQuery(DBManager.ADD_COUPON_PURCHASE, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE);
        }
    }

    /**
     * Returns an instance of Coupon from MySQL database by coupon ID number.
     *
     * @param couponId Coupon ID number
     * @return Coupon instance from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Coupon readCoupon(Integer couponId) throws EntityCrudException {
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPON_BY_ID, params);
            assert result != null;
            result.next();
            return ObjectExtractionUtil.resultSetToCoupon(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Coupons in MySQL database.
     *
     * @return List of all Coupons in MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readAllCoupons() throws EntityCrudException {
        ResultSet result;
        List<Coupon> couponList = new ArrayList<>();
        try {
            result = DBTools.runQueryForResult(DBManager.READ_ALL_COUPONS);
            while (result.next()) {
                couponList.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return couponList;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Coupons a Customer owns by customer ID number from MySQL database.
     *
     * @param customerId Customer ID number
     * @return List of all Coupons by customer ID from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readCouponsByCustomerId(Integer customerId) throws EntityCrudException {
        ResultSet result;
        List<Coupon> couponList = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_CUSTOMER_ID, params);
            while (result.next()) {
                couponList.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return couponList;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Coupons a Customer owns by customer ID number and maximum price threshold from MySQL database.
     *
     * @param customerId Customer ID number
     * @param maxPrice   Maximum price that Coupon price in the list can not exceed
     * @return List of all Coupons by customer ID and maximum price from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readCouponsByCustomerIdAndMaxPrice(Integer customerId, Double maxPrice) throws EntityCrudException {
        ResultSet result;
        List<Coupon> couponList = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, maxPrice);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_CUSTOMER_ID_AND_MAX_PRICE, params);
            while (result.next()) {
                couponList.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return couponList;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Coupons a Customer owns by customer ID number and Coupon category from MySQL database.
     *
     * @param customerId Customer ID number
     * @param category   Category that Coupons belong to
     * @return List of all Coupons by customer ID and category from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readCouponsByCustomerIdAndCategory(Integer customerId, String/*todo why not Category enum?*/ category) throws EntityCrudException {
        ResultSet result;
        List<Coupon> couponList = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, category);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_CUSTOMER_ID_AND_CATEGORY, params);
            while (result.next()) {
                couponList.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return couponList;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Coupons a Company issued by company ID number from MySQL database.
     *
     * @param companyId Company ID number
     * @return List of all Coupons by company ID from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readCouponsByCompanyId(Integer companyId) throws EntityCrudException {
        ResultSet result;
        List<Coupon> couponList = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_COMPANY_ID, params);
            while (result.next()) {
                couponList.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return couponList;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Coupons a Company issued by company ID number and maximum price threshold from MySQL database.
     *
     * @param companyId Company ID number
     * @param maxPrice  Maximum price that Coupon price in the list can not exceed
     * @return List of all Coupons by company ID and maximum price from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readCouponsByCompanyIdAndMaxPrice(Integer companyId, Double maxPrice) throws EntityCrudException {
        ResultSet result;
        List<Coupon> couponList = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, maxPrice);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_COMPANY_ID_AND_MAX_PRICE, params);
            while (result.next()) {
                couponList.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return couponList;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Coupons a Company issued by company ID number and Coupon category from MySQL database.
     *
     * @param companyId Company ID number
     * @param category  Category that Coupons belong to
     * @return List of all Coupons by company ID and category from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readCouponsByCompanyIdAndCategory(Integer companyId, String/*todo why not Category enum?*/ category) throws EntityCrudException {
        ResultSet result;
        List<Coupon> couponList = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, category);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_COMPANY_ID_AND_CATEGORY, params);
            while (result.next()) {
                couponList.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return couponList;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Updates Coupon record in MySQL database. Can not update Coupon ID number, Company ID number or Start date.
     *
     * @param coupon Coupon instance to update by
     * @throws EntityCrudException Thrown if update in MySQL was unsuccessful
     */
    @Override
    public void updateCoupon(Coupon coupon) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getTitle());
        params.put(2, coupon.getCategory());
        params.put(3, coupon.getAmount());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getPrice());
        params.put(6, coupon.getImage());
        params.put(7, coupon.getEndDate());
        params.put(8, coupon.getId());
        try {
            System.out.println("Updated Coupon: " + DBTools.runQuery(DBManager.UPDATE_COUPON_BY_ID, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.UPDATE);
        }
    }

    /**
     * Deletes Coupon record from MySQL database by coupon ID number.
     *
     * @param couponID ID number of the Coupon to be deleted
     * @throws EntityCrudException Thrown if delete from MySQL was unsuccessful
     */
    @Override
    public void deleteCoupon(Integer couponID) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);
        try {
            System.out.println("Deleted Coupon: " + DBTools.runQuery(DBManager.DELETE_COUPON_BY_ID, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        }
    }

    /**
     * Deletes Coupon records from MySQL database by coupon expiration date.
     *
     * @param date current date
     * @throws EntityCrudException Thrown if delete from MySQL was unsuccessful
     */
    @Override
    public void deleteExpiredCoupons(String date) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, date);
        try {
            System.out.println("Deleted Coupon: " + DBTools.runQuery(DBManager.DELETE_COUPON_BY_END_DATE, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        }
    }

    /**
     * Checks whether the Coupon corresponding to the title argument exists in the Company corresponding to the company
     * ID argument in MySQL database by counting the coupons that meet both criteria.
     *
     * @param companyId Issuing Company ID number
     * @param title     Coupon title
     * @return true -> coupon exists, false -> coupon does not exist
     * @throws EntityCrudException Thrown if count in MySQL was unsuccessful
     */
    @Override
    public boolean isCouponExistByCompanyId(Integer companyId, String title) throws EntityCrudException {
        int counter;
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, title);
        try {
            result = DBTools.runQueryForResult(DBManager.COUNT_COUPONS_BY_COMPANY_ID_AND_TITLE, params);
            assert result != null;
            result.next();
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.COUNT);
        }
    }

    /**
     * Checks whether the purchase of a Coupon corresponding to the coupon ID by a Customer corresponding to the customer ID
     * exists in MySQL database by counting the purchases that meet both criteria.
     *
     * @param couponId   The Coupon ID being checked
     * @param customerId The Customer ID being checked
     * @return true -> purchase of the coupon by the customer exists, false -> purchase does not exist
     * @throws EntityCrudException Thrown if count in MySQL was unsuccessful
     */
    @Override
    public boolean isPurchaseExistByIds(Integer couponId, Integer customerId) throws EntityCrudException {
        int counter;
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        params.put(2, customerId);
        try {
            result = DBTools.runQueryForResult(DBManager.COUNT_PURCHASE_BY_IDS, params);
            assert result != null;
            result.next();
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.COUNT);
        }
    }
}

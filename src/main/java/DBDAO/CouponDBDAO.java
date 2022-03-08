package DBDAO;


import Beans.Category;
import Beans.Coupon;
import DB.ConnectionPool;
import DB.Util.DBManager;
import DB.Util.DBTools;
import DB.Util.ObjectExtractionUtil;
import Exceptions.CrudOperation;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponDBDAO implements CouponDAO {
    private static CouponDBDAO instance = null;

    private CouponDBDAO() {
    }

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
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Coupons in MySQL database
     *
     * @return List of all Coupons in MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readAllCoupons() throws EntityCrudException {
        ResultSet result;
        try {
            result = DBTools.runQueryForResult(DBManager.READ_ALL_COUPONS);
            List<Coupon> coupons = new ArrayList<>();
            while (result.next()) { // todo consider asserting
                coupons.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return coupons;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Coupons a Customer owns by customer ID number from MySQL database
     *
     * @param customerId Customer ID number
     * @return List of all Coupons by customer ID from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readCouponsByCustomerId(Integer customerId) throws EntityCrudException {
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_CUSTOMER_ID, params);
            List<Coupon> coupons = new ArrayList<>();
            while (result.next()) { // todo consider asserting
                coupons.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return coupons;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    @Override
    public List<Coupon> readCouponsByCustomerIdAndMaxPrice(Integer customerId, Double price) throws EntityCrudException {
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, price);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_CUSTOMER_ID_AND_MAX_PRICE, params);
            List<Coupon> coupons = new ArrayList<>();
            while (result.next()) { // todo consider asserting
                coupons.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return coupons;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    @Override
    public List<Coupon> readCouponsByCustomerIdAndCategory(Integer customerId, String category) throws EntityCrudException {
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, category);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_CUSTOMER_ID_AND_CATEGORY, params);
            List<Coupon> coupons = new ArrayList<>();
            while (result.next()) { // todo consider asserting
                coupons.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return coupons;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

    @Override
    public List<Coupon> readCouponsByCompanyId(final Integer companyId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ?";
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            final ResultSet result = preparedStatement.executeQuery();

            final List<Coupon> coupons = new ArrayList<>();
            while (result.next()) {
                coupons.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }

            return coupons;
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Coupon> readCouponsByCompanyIdAndMaxPrice(Integer companyId, String price) throws EntityCrudException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ? AND price <= ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            preparedStatement.setString(2, price); //todo:check why Price is String in SQL
            final ResultSet result = preparedStatement.executeQuery();

            final List<Coupon> coupons = new ArrayList<>();
            while (result.next()) {
                coupons.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }

            return coupons;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Coupon> readCouponsByCompanyIdAndCategory(Integer companyId, String category) throws EntityCrudException, SQLException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ? AND category = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            preparedStatement.setString(2, category);
            final ResultSet result = preparedStatement.executeQuery();

            final List<Coupon> coupons = new ArrayList<>();
            while (result.next()) {
                coupons.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }

            return coupons;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * Updates Coupon record in MySQL database.
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
        params.put(7, coupon.getStartDate());
        params.put(8, coupon.getEndDate());
        params.put(9, coupon.getId());
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
     * @param date current date //todo correct?
     * @throws EntityCrudException Thrown if delete from MySQL was unsuccessful
     */
    @Override
    public void deleteExpiredCoupons(String date/*todo why string?*/) throws EntityCrudException {
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
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.COUNT);
        }
    }
}

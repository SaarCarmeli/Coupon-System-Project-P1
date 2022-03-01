package DBDAO;


import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DB.ConnectionPool;
import DB.Util.ObjectExtractionUtils;
import Exceptions.CrudOperation;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponDBDAO implements CouponDAO {
    private static CouponDBDAO instance = null;
    private final ConnectionPool connectionPool;

    private CouponDBDAO() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting connection pool instance");
        }
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

    @Override
    public Integer createCoupon(Coupon coupon) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement =
                    "INSERT INTO coupons (title, company_id, start_date, end_date, amount, category, description, price, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(
                    sqlStatement, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, coupon.getTitle());
            preparedStatement.setInt(2, coupon.getCompanyID());
            preparedStatement.setString(3, coupon.getStartDate().toString());
            preparedStatement.setString(4, coupon.getEndDate().toString());
            preparedStatement.setInt(5, coupon.getAmount());
            preparedStatement.setString(6, coupon.getCategory());
            preparedStatement.setString(7, coupon.getDescription());
            preparedStatement.setDouble(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getImage());
            preparedStatement.executeUpdate();
            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                throw new RuntimeException("Failed to create another coupon");
            }

            return generatedKeysResult.getInt(1);
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Coupon readCoupon(Integer couponId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM coupons WHERE id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, couponId);
            final ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtils.resultSetToCoupon(result);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    @Override
    public List<Coupon> readAllCoupons() throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM coupons";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            final ResultSet result = preparedStatement.executeQuery();

            final List<Coupon> coupons = new ArrayList<>();
            while (result.next()) {
                coupons.add(ObjectExtractionUtils.resultSetToCoupon(result));
            }

            return coupons;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "UPDATE coupons SET title = ?, category = ? ,amount = ? ," +
                    "description = ? ,price = ? ,image = ? , start_date = ? ,end_date = ?";
            PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, coupon.getTitle());
            preparedStatement.setString(2, coupon.getCategory());
            preparedStatement.setInt(3, coupon.getAmount());
            preparedStatement.setString(4, coupon.getDescription());
            preparedStatement.setDouble(5, coupon.getPrice());
            preparedStatement.setString(6, coupon.getImage());
            preparedStatement.setDate(7, coupon.getStartDate());
            preparedStatement.setDate(8, coupon.getEndDate());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.UPDATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteCoupon(Integer couponID) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            deleteCouponHistory(couponID);
            final String sqlStatement = "DELETE FROM coupons WHERE id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, couponID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteExpiredCoupons(String date) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            //todo:fix the SQL statement
            final String sqlStatement = "DELETE FROM coupons WHERE end_date < ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, date);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * Checks whether the Coupon corresponding to the title argument exists in the corresponding Company in MySQL database
     *
     * @param companyId ID of the checked Company
     * @param title     title of the Coupon being checked
     * @return true -> coupon exists in the company, false -> coupon does not exist in company
     * @throws EntityCrudException thrown if operation failed
     */
    @Override
    public boolean isCouponExistByCompanyId(Integer companyId, String title) throws EntityCrudException {
        Connection connection = null;
        int counter;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT count(*) FROM coupons WHERE company_id = ? AND title = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            preparedStatement.setString(2, title);
            final ResultSet result = preparedStatement.executeQuery();
            result.next();
            counter = result.getInt(1);
            return counter != 0;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.COUNT);
        } finally {
            connectionPool.returnConnection(connection);
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
                coupons.add(ObjectExtractionUtils.resultSetToCoupon(result));
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
                coupons.add(ObjectExtractionUtils.resultSetToCoupon(result));
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
                coupons.add(ObjectExtractionUtils.resultSetToCoupon(result));
            }

            return coupons;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void addCouponPurchase(Integer couponId, Integer customerId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement =
                    "INSERT INTO customer_to_coupon (customer_id,coupon_id) VALUES (?,?)";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, couponId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteCouponPurchase(Integer couponId, Integer customerId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement =
                    "DELETE FROM customer_to_coupon WHERE customer_id = ? AND coupon_id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, couponId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteCouponHistory(Integer couponID) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "DELETE FROM customer_to_coupons WHERE id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, couponID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteAllCouponsByCompanyId(Integer companyId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "DELETE FROM coupons WHERE companies_id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}

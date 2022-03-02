package DBDAO;

import Beans.Company;
import DB.ConnectionPool;
import DB.Util.ObjectExtractionUtil;
import Exceptions.CrudOperation;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDBDAO implements CompanyDAO {
    private static CompanyDBDAO instance = null;

    private final ConnectionPool connectionPool;
    private final CouponDBDAO couponDBDAO;

    private CompanyDBDAO() {
        couponDBDAO = CouponDBDAO.getInstance();
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting connection pool instance");
        }
    }

    public static CompanyDBDAO getInstance() {
        if (instance == null) {
            synchronized (CompanyDBDAO.class) {
                if (instance == null) {
                    instance = new CompanyDBDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public Integer createCompany(Company company) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "INSERT INTO companies (name, email, password) VALUES(?, ?, ?)";
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlStatement, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setString(3, company.getPassword());
            preparedStatement.executeUpdate();
            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                throw new RuntimeException("No results");
            }

            return generatedKeysResult.getInt(1);
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Company readCompany(Integer companyId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM companies WHERE id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            final ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtil.resultSetToCompany(result, couponDBDAO.readCouponsByCompanyId(companyId));
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Company> readAllCompanies() throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM companies";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            final ResultSet result = preparedStatement.executeQuery();

            final List<Company> companies = new ArrayList<>();
            while (result.next()) {
                companies.add(ObjectExtractionUtil.resultSetToCompany(result));
            }

            return companies;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateCompany(Company company) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "UPDATE companies SET email = ?, password = ?";
            PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, company.getEmail());
            preparedStatement.setString(2, company.getPassword());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.UPDATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteCompany(Integer companyId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "DELETE FROM companies WHERE id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * Checks whether the Company corresponding to the name or email arguments exists in MySQL database
     *
     * @param name  Company name
     * @param email Company email
     * @return true -> company exists, false -> company does not exist
     * @throws EntityCrudException thrown if operation failed
     */
    @Override
    public boolean isCompanyExist(String name, String email) throws EntityCrudException {
        Connection connection = null;
        int counter;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT count(*) FROM companies WHERE name = ? OR email = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
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

    public void deleteCompanyPurchaseHistory(Integer companyId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            //1
            couponDBDAO.readCouponsByCompanyId(companyId);
            //2
            final String sqlStatement = "SELECT FROM customer_to_coupon WHERE coupon_id = ? AND SELECT FROM coupons WHERE company_id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, companyId);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
        //todo:create a help method to check if coupon_id inside customer_to_coupons equals in coupons to company_id that we want to delete;
    }
}

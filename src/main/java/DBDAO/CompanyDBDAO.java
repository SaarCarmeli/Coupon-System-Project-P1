package DBDAO;

import Beans.Company;
import DB.ConnectionPool;
import DB.Util.ObjectExtractionUtils;
import Exceptions.CrudOperation;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDBDAO extends UserDAO<Integer, Company> {
    public static final CompanyDBDAO instance = new CompanyDBDAO();

    private final ConnectionPool connectionPool;
    private final CouponDBDAO couponDBDAO = CouponDBDAO.instance;

    private CompanyDBDAO() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting connection pool instance");
        }
    }

    public Integer create(final Company company) throws EntityCrudException {
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

    public Company readById(final Integer companyId) throws EntityCrudException {
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

            return ObjectExtractionUtils.resultSetToCompany(result, couponDBDAO.readCouponsByCompanyId(companyId));
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Company> readAll() throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM companies";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            final ResultSet result = preparedStatement.executeQuery();

            final List<Company> companies = new ArrayList<>();
            while (result.next()) {
                companies.add(ObjectExtractionUtils.resultSetToCompany(result));
            }

            return companies;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void update(final Company company) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "UPDATE companies SET name = ?, email = ?, password = ?";
            PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setString(3, company.getPassword());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.UPDATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void delete(final Integer companyId) throws EntityCrudException {
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


    public Company readByEmail(final String email) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM companies WHERE email = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, email);
            final ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtils.resultSetToCompany(result);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
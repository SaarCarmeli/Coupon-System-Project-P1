package DBDAO;

import Beans.Coupon;
import Beans.Customer;
import DB.ConnectionPool;
import DB.Util.ObjectExtractionUtils;
import Exceptions.CrudOperation;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDBDAO extends UserDAO<Integer, Customer> {
    public static final CustomerDBDAO instance = new CustomerDBDAO();
    private final ConnectionPool connectionPool;

    private CustomerDBDAO() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting connection pool instance");
        }
    }

    @Override
    public Integer create(Customer customer) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "INSERT INTO customers (first_name, last_name, email, password) VALUES(?, ?, ?, ?)";
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlStatement, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.executeUpdate();
            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                throw new RuntimeException("No results");
            }

            return generatedKeysResult.getInt(1);
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    @Override
    public Customer readById(Integer customerId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM customers WHERE id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customerId);
            final ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtils.resultSetToCustomer(result, readCouponsByCustomerId(customerId));
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    @Override
    public List<Customer> readAll() throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM customers";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            final ResultSet result = preparedStatement.executeQuery();

            final List<Customer> customers = new ArrayList<>();
            while (result.next()) {
                customers.add(ObjectExtractionUtils.resultSetToCustomer(result));
            }

            return customers;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void update(Customer customer) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "UPDATE customers SET first_name = ?,last_name = ?, email = ?, password = ?";
            PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.UPDATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    @Override
    public void delete(Integer customerId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "DELETE FROM customers WHERE id = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Coupon> readCouponsByCustomerId(final Integer customerId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM customer_to_coupon WHERE customer_id = ?";
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customerId);
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
    public Customer readByEmail(String email) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "SELECT * FROM customers WHERE email = ?";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, email);
            final ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtils.resultSetToCustomer(result);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}

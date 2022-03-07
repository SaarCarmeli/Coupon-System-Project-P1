package DBDAO;

import Beans.Coupon;
import Beans.Customer;
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

public class CustomerDBDAO implements CustomerDAO {
    private static CustomerDBDAO instance;

    private CustomerDBDAO() {
    }

    public static CustomerDBDAO getInstance() {
        if (instance == null) {
            synchronized (CustomerDBDAO.class) {
                if (instance == null) {
                    instance = new CustomerDBDAO();
                }
            }
        }
        return instance;
    }

    @Override// todo finish
    public Integer createCustomer(Customer customer) throws EntityCrudException {
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
            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys(); // todo adapt generic method to return generated-keys

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

    /**
     * Returns an instance of Customer from MySQL database by customer ID number.
     *
     * @param customerId Customer ID number
     * @return Customer instance from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Customer readCustomer(Integer customerId) throws EntityCrudException {
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_CUSTOMER_BY_ID, params);
            assert result != null;
            result.next(); //todo needed?
            return ObjectExtractionUtil.resultSetToCustomer(result, CouponDBDAO.getInstance().readCouponsByCustomerId(customerId));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Customers in MySQL database
     *
     * @return List of all Customers in MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Customer> readAllCustomers() throws EntityCrudException {
        ResultSet result;
        try {
            result = DBTools.runQueryForResult(DBManager.READ_ALL_CUSTOMERS);
            List<Customer> customers = new ArrayList<>();
            while (result.next()) { // todo consider asserting
                customers.add(ObjectExtractionUtil.resultSetToCustomer(result));
            }
            return customers;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        }
    }

    /**
     * Updates Customer record in MySQL database.
     *
     * @param customer Customer instance to update by
     * @throws EntityCrudException Thrown if update in MySQL was unsuccessful
     */
    @Override
    public void updateCustomer(Customer customer) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword()); // todo should it be in the database???
        params.put(5, customer.getId());
        try {
            System.out.println("Updated Customer: " + DBTools.runQuery(DBManager.UPDATE_CUSTOMER_BY_ID, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.UPDATE);
        }
    }

    /**
     * Deletes Customer record from MySQL database by customer ID number.
     *
     * @param customerId ID number of the Customer to be deleted
     * @throws EntityCrudException Thrown if delete from MySQL was unsuccessful
     */
    @Override
    public void deleteCustomer(Integer customerId) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        try {
            System.out.println("Deleted Customer: " + DBTools.runQuery(DBManager.DELETE_CUSTOMER_BY_ID, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.DELETE);
        }
    }

    /**
     * Checks whether the Customer corresponding to the email argument exists in MySQL database
     * by counting the customers that meet the criteria.
     *
     * @param email Customer email
     * @return true -> customer exists, false -> customer does not exist
     * @throws EntityCrudException Thrown if count in MySQL was unsuccessful
     */
    @Override
    public boolean isCustomerExist(String email) throws EntityCrudException {
        int counter;
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        try {
            result = DBTools.runQueryForResult(DBManager.COUNT_CUSTOMERS_BY_EMAIL, params);
            assert result != null;
            result.next(); //todo needed?
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.COUNT);
        }
    }
}



package DBDAO;

import Beans.Customer;
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
 * Class containing all Create-Read-Update-Delete methods for "customers" tables.
 */
public class CustomerDBDAO implements CustomerDAO {
    private static CustomerDBDAO instance;

    /**
     * Private constructor for CustomerDBDAO.
     */
    private CustomerDBDAO() {
    }

    /**
     * Static method for retrieving and/or initiating an instance of CustomerDBDAO.
     *
     * @return CustomerDBDAO instance
     */
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

    /**
     * Create Customer record in MySQL database.
     *
     * @param customer Customer instance to create record by
     * @throws EntityCrudException Thrown if Create in MySQL was unsuccessful
     */
    @Override
    public void createCustomer(Customer customer) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        try {
            System.out.println("Created Customer: " + DBTools.runQuery(DBManager.CREATE_CUSTOMER, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.CREATE);
        }
    }

    /**
     * Returns an instance of Customer from MySQL database by customer ID number.
     * Returns Customer ID number, First name, Last name and Email. Does not return password.
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
            result.next();
            return ObjectExtractionUtil.resultSetToCustomer(result, CouponDBDAO.getInstance().readCouponsByCustomerId(customerId));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Customers in MySQL database.
     * Returns Customer ID number, First name, Last name and Email. Does not return password.
     *
     * @return List of all Customers in MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Customer> readAllCustomers() throws EntityCrudException {
        ResultSet result;
        List<Customer> customerList = new ArrayList<>();
        try {
            result = DBTools.runQueryForResult(DBManager.READ_ALL_CUSTOMERS);
            while (result.next()) {
                customerList.add(ObjectExtractionUtil.resultSetToCustomer(result));
            }
            return customerList;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        }
    }

    /**
     * Updates Customer record in MySQL database.
     * Updates First name, Last name, email and password. Does not update Customer ID number.
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
        params.put(4, customer.getPassword());
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
            result.next();
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.COUNT);
        }
    }
}



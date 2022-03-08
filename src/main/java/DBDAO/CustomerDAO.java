package DBDAO;

import Beans.Coupon;
import Beans.Customer;
import Exceptions.EntityCrudException;

import java.util.List;

public interface CustomerDAO {
    void createCustomer(Customer customer) throws EntityCrudException;
    Customer readCustomer(Integer customerId) throws EntityCrudException;
    List<Customer> readAllCustomers() throws EntityCrudException;
    void updateCustomer(Customer customer) throws EntityCrudException;
    void deleteCustomer(Integer customerId) throws EntityCrudException;
    boolean isCustomerExist(String email) throws EntityCrudException;
}

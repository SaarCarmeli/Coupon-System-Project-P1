package DBDAO;

import Beans.Coupon;
import Beans.Customer;
import Exceptions.EntityCrudException;

import java.util.List;

public interface CustomerDAO {
    Integer createCustomer(Customer customer) throws EntityCrudException;
    Customer readCustomer(Integer customerId) throws EntityCrudException;
    List<Customer> readAllCustomers() throws EntityCrudException;
    List<Coupon> readCouponsByCustomerId(Integer customerId) throws EntityCrudException;
    void updateCustomer(Customer customer) throws EntityCrudException;
    void deleteCustomer(Integer customerId) throws EntityCrudException;
    boolean isCustomerExist(String email) throws EntityCrudException;
    void deleteCouponPurchaseHistory(Integer customerId) throws EntityCrudException;
}

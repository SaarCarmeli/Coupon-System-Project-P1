package Facades;

import Beans.Company;
import Beans.Customer;
import Exceptions.EntityCrudException;

import java.util.ArrayList;

public interface AdminFacadeDAO {
    void addCompany(Company company) throws Exception;
    void updateCompany(Company company);
    void deleteCompany(int companyId);
    Company readCompany(int companyId) throws EntityCrudException;
    ArrayList<Company> getAllCompanies() throws EntityCrudException;

    void addCustomer(Customer customer) throws Exception;
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerId);
    Customer readCustomer(int customerId) throws EntityCrudException;
    ArrayList<Customer> getAllCustomers() throws EntityCrudException;
}

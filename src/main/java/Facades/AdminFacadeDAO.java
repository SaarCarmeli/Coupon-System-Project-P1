package Facades;

import Beans.Company;
import Beans.Customer;
import Exceptions.EntityCrudException;

import java.util.ArrayList;

public interface AdminFacadeDAO {
    void addCompany(Company company) throws Exception; //todo custom exception
    void updateCompany(Company company) throws EntityCrudException;
    void deleteCompany(Integer companyId) throws EntityCrudException;
    Company readCompany(Integer companyId) throws EntityCrudException;
    ArrayList<Company> getAllCompanies() throws EntityCrudException;

    void addCustomer(Customer customer) throws Exception; //todo custom exception
    void updateCustomer(Customer customer) throws EntityCrudException;
    void deleteCustomer(Integer customerId) throws EntityCrudException;
    Customer readCustomer(Integer customerId) throws EntityCrudException;
    ArrayList<Customer> getAllCustomers() throws EntityCrudException;
}

package Facades;

import Beans.Company;
import Beans.Customer;
import DBDAO.CompanyDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.EntityCrudException;

import java.util.ArrayList;

public class AdminFacade implements AdminFacadeDAO {

    public AdminFacade() {
    }

    @Override
    public void addCompany(Company company) throws Exception {
        if (CompanyDBDAO.getInstance().isCompanyExist(company.getName(), company.getEmail())) {
            throw new Exception(); //todo custom exception
        }
        CompanyDBDAO.getInstance().createCompany(company);
    }

    @Override
    public void updateCompany(Company company) {

    }

    @Override
    public void deleteCompany(int companyId) {

    }

    @Override
    public Company readCompany(int companyId) throws EntityCrudException {
        return CompanyDBDAO.getInstance().readCompany(companyId);
    }

    @Override
    public ArrayList<Company> getAllCompanies() throws EntityCrudException {
        return (ArrayList<Company>) CompanyDBDAO.getInstance().readAllCompanies();
    }

    @Override
    public void addCustomer(Customer customer) throws Exception {
        if (CustomerDBDAO.getInstance().isCustomerExist(customer.getEmail())) {
            throw new Exception(); //todo custom exception
        }
        CustomerDBDAO.getInstance().createCustomer(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public void deleteCustomer(int customerId) {

    }

    @Override
    public Customer readCustomer(int customerId) throws EntityCrudException {
        return CustomerDBDAO.getInstance().readCustomer(customerId);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws EntityCrudException {
        return (ArrayList<Customer>) CustomerDBDAO.getInstance().readAllCustomers();
    }
}
package Facades;

import Beans.Company;
import Beans.Customer;
import DBDAO.CompanyDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.util.ArrayList;

public class AdminFacade implements AdminFacadeDAO {

    public AdminFacade() {
    }

    @Override
    public void addCompany(Company company) throws EntityAlreadyExistException, EntityCrudException {
        if (CompanyDBDAO.getInstance().isCompanyExist(company.getName(), company.getEmail())) {
            throw new EntityAlreadyExistException(EntityType.COMPANY);
        }
        CompanyDBDAO.getInstance().createCompany(company);
    }

    @Override
    public void updateCompany(Company company) throws EntityCrudException {
        CompanyDBDAO.getInstance().updateCompany(company);
    }

    @Override
    public void deleteCompany(Integer companyId) throws EntityCrudException {
        CouponDBDAO.getInstance().deleteCouponsByCompanyId(companyId);
        CompanyDBDAO.getInstance().deleteCompanyPurchaseHistory(companyId);
        CompanyDBDAO.getInstance().deleteCompany(companyId);
    }

    @Override
    public Company readCompany(Integer companyId) throws EntityCrudException {
        return CompanyDBDAO.getInstance().readCompany(companyId);
    }

    @Override
    public ArrayList<Company> getAllCompanies() throws EntityCrudException {
        return (ArrayList<Company>) CompanyDBDAO.getInstance().readAllCompanies();
    }

    @Override
    public void addCustomer(Customer customer) throws EntityAlreadyExistException, EntityCrudException {
        if (CustomerDBDAO.getInstance().isCustomerExist(customer.getEmail())) {
            throw new EntityAlreadyExistException(EntityType.CUSTOMER);
        }
        CustomerDBDAO.getInstance().createCustomer(customer);
    }

    @Override
    public void updateCustomer(Customer customer) throws EntityCrudException {
        CustomerDBDAO.getInstance().updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(Integer customerId) throws EntityCrudException {
        CustomerDBDAO.getInstance().deleteCouponPurchaseHistory(customerId);
        CustomerDBDAO.getInstance().deleteCustomer(customerId);
    }

    @Override
    public Customer readCustomer(Integer customerId) throws EntityCrudException {
        return CustomerDBDAO.getInstance().readCustomer(customerId);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws EntityCrudException {
        return (ArrayList<Customer>) CustomerDBDAO.getInstance().readAllCustomers();
    }
}

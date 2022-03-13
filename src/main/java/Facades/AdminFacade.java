package Facades;

import Beans.Company;
import Beans.Customer;
import DBDAO.CompanyDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.util.ArrayList;

public class AdminFacade implements AdminFacadeDAO {

    /**
     * Constructs an AdminFacade that implements the methods an administrator is allowed to use. Accessed via log-in from the LoginManager.
     */
    public AdminFacade() {
    }

    /**
     * Creates Company record in the database. Can not create a Company with the same name or the email as another Company.
     *
     * @param company Company instance to create the record by
     * @throws EntityAlreadyExistException Thrown if a Company with the same name or email exists in the database
     * @throws EntityCrudException         Thrown if Create in MySQL was unsuccessful
     */
    @Override
    public void addCompany(Company company) throws EntityAlreadyExistException, EntityCrudException {
        if (CompanyDBDAO.getInstance().isCompanyExist(company.getName(), company.getEmail())) {
            throw new EntityAlreadyExistException(EntityType.COMPANY);
        }
        CompanyDBDAO.getInstance().createCompany(company);
    }

    /**
     * Updates Company record in the database. Can not update Company ID number or Company name.
     *
     * @param company Company instance to update the record by
     * @throws EntityCrudException Thrown if Update in MySQL was unsuccessful
     */
    @Override
    public void updateCompany(Company company) throws EntityCrudException {
        CompanyDBDAO.getInstance().updateCompany(company);
    }

    /**
     * Deletes Company record from the database as well as all Coupons the company issued and their purchase history by Customers.
     *
     * @param companyId Company ID number
     * @throws EntityCrudException Thrown if Delete in MySQL was unsuccessful
     */
    @Override
    public void deleteCompany(Integer companyId) throws EntityCrudException {
        CompanyDBDAO.getInstance().deleteCompany(companyId);
    }

    /**
     * Returns the details of a Company by its ID number, including:
     * Company ID number, Name and Email
     *
     * @param companyId Company ID number
     * @return Company details from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Company readCompany(Integer companyId) throws EntityCrudException {
        return CompanyDBDAO.getInstance().readCompany(companyId);
    }

    /**
     * Returns the details of all Companies, including:
     * Company ID number, Name and Email
     *
     * @return List of all Companies details from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public ArrayList<Company> getAllCompanies() throws EntityCrudException {
        return (ArrayList<Company>) CompanyDBDAO.getInstance().readAllCompanies();
    }

    /**
     * Creates Customer record in the database. Can not create a Customer with the same email as another Customer.
     *
     * @param customer Customer instance to create the record by
     * @throws EntityAlreadyExistException Thrown if a Customer with the same email exists in the database
     * @throws EntityCrudException         Thrown if Create in MySQL was unsuccessful
     */
    @Override
    public void addCustomer(Customer customer) throws EntityAlreadyExistException, EntityCrudException {
        if (CustomerDBDAO.getInstance().isCustomerExist(customer.getEmail())) {
            throw new EntityAlreadyExistException(EntityType.CUSTOMER);
        }
        CustomerDBDAO.getInstance().createCustomer(customer);
    }

    /**
     * Updates Customer record in the database. Can not update Customer ID number.
     *
     * @param customer Customer instance to update the record by
     * @throws EntityCrudException Thrown if Update in MySQL was unsuccessful
     */
    @Override
    public void updateCustomer(Customer customer) throws EntityCrudException {
        CustomerDBDAO.getInstance().updateCustomer(customer);
    }

    /**
     * Deletes Customer record from the database as well as his Coupon purchase history.
     *
     * @param customerId Customer ID number
     * @throws EntityCrudException Thrown if Delete from MySQL was unsuccessful
     */
    @Override
    public void deleteCustomer(Integer customerId) throws EntityCrudException {
        CustomerDBDAO.getInstance().deleteCustomer(customerId);
    }

    /**
     * Returns the details of a Customer by his ID number, including:
     * Customer ID number, First name, Last name and Email
     *
     * @param customerId Customer ID number
     * @return Customer details from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Customer readCustomer(Integer customerId) throws EntityCrudException {
        return CustomerDBDAO.getInstance().readCustomer(customerId);
    }

    /**
     * Returns the details of all Customers, including:
     * Customer ID number, First name, Last name and Email
     *
     * @return List of all Customers details from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public ArrayList<Customer> getAllCustomers() throws EntityCrudException {
        return (ArrayList<Customer>) CustomerDBDAO.getInstance().readAllCustomers();
    }
}

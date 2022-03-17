package Tests.LoginTests;

import Beans.Company;
import Beans.Customer;
import Beans.Util.TablePrinterUtil;
import DB.DatabaseInitializer;
import DB.Util.DBManager;
import DB.Util.DBTools;
import DBDAO.CompanyDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Facades.AdminFacade;
import LoginManager.ClientType;
import LoginManager.LoginManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class AdminFacadeTest {
    public static AdminFacade adminFacade;
    public static int idCounter = 1;
    public static Consumer<Company> companyCreation = company -> {
        try {
            adminFacade.addCompany(company);
        } catch (EntityAlreadyExistException | EntityCrudException e) {
            System.out.println(e.getMessage());
        }
    };
    public static Consumer<Company> companyAssertion = company -> {
        try {
            assertEquals(company.getId(), adminFacade.readCompany(idCounter).getId());
            assertEquals(company.getName(), adminFacade.readCompany(idCounter).getName());
            assertEquals(company.getEmail(), adminFacade.readCompany(idCounter).getEmail());
            idCounter++;
        } catch (EntityCrudException e) {
            System.out.println(e.getMessage());
        }
    };

    public static Consumer<Customer> customerCreation = customer -> {
        try {
            adminFacade.addCustomer(customer);
        } catch (EntityAlreadyExistException | EntityCrudException e) {
            System.out.println(e.getMessage());
        }
    };

    public static Consumer<Customer> customerAssertion = customer -> {
        try {
            assertEquals(customer.getId(), adminFacade.readCustomer(idCounter).getId());
            assertEquals(customer.getFirstName(), adminFacade.readCustomer(idCounter).getFirstName());
            assertEquals(customer.getLastName(), adminFacade.readCustomer(idCounter).getLastName());
            assertEquals(customer.getEmail(), adminFacade.readCustomer(idCounter).getEmail());
            idCounter++;
        } catch (EntityCrudException e) {
            System.out.println(e.getMessage());
        }
    };

    @Before
    public void initiation() {
        DatabaseInitializer.createTables();
        adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR"));
        idCounter = 1;
    }

    @After
    public void finish() throws SQLException {
        System.out.println("Dropped schema: " + DBTools.runQuery(DBManager.DROP_SCHEMA));
    }

    @Test
    public void addCompanyTest() throws Exception {
        Company company = new Company("Itzik hooBanav", "itzB@itzmail.com", "19itzbanav_50");
        Company newCompany = new Company(idCounter, "Itzik hooBanav", "itzB@itzmail.com");
        adminFacade.addCompany(company);
        companyAssertion.accept(newCompany);
    }

    @Test
    public void updateCompanyTest() throws Exception {
        String password = "12345678";
        Company company = new Company("Tzmigey Ferdinand von Schlauffer", "Tzmigimail@TzamigBusinessMail.com", password);
        adminFacade.addCompany(company);
        company = adminFacade.readCompany(idCounter);
        company.setPassword(password);
        company.setEmail("businessMail@TzamigMail.com");
        adminFacade.updateCompany(company);
        companyAssertion.accept(company);
    }

    @Test
    public void deleteCompanyTest() throws Exception {
        Company company = new Company("Motti Hovalot", "Motti@Mmail.com", "abc123");
        adminFacade.addCompany(company);
        assertTrue(TestDBMethods.isCompanyExistById(idCounter));
        adminFacade.deleteCompany(idCounter);
        assertFalse(TestDBMethods.isCompanyExistById(idCounter));
    }

    @Test
    public void readAllCompaniesTest() throws Exception {
        Company company1 = new Company("Motti Hovalot", "Motti@Mmail.com", "abc123");
        Company company2 = new Company("Itzik hooBanav", "itzB@itzmail.com", "19itzbanav_50");
        Company company3 = new Company("Macrohard Corp.", "MacroBusiness@coldmail.com", "secretlyMicrosoft");
        List<Company> companyList = List.of(company1, company2, company3);
        companyList.forEach(company -> companyCreation.accept(company));
        List<Company> newCompanyList = adminFacade.readAllCompanies();
        newCompanyList.forEach(company -> companyAssertion.accept(company));
    }

    @Test
    public void printAllCompaniesTest() throws Exception {
        Company company1 = new Company("Motti Hovalot", "Motti@Mmail.com", "abc123");
        Company company2 = new Company("Itzik hooBanav", "itzB@itzmail.com", "19itzbanav_50");
        Company company3 = new Company("Macrohard Corp.", "MacroBusiness@coldmail.com", "secretlyMicrosoft");
        List<Company> companyList = List.of(company1, company2, company3);
        companyList.forEach(company -> companyCreation.accept(company));
        TablePrinterUtil.print(adminFacade.readAllCompanies());
    }

    @Test
    public void addCustomerTest() throws Exception {
        Customer customer = new Customer("Jeffery", "Jefferson", "jeffjeff@gmail.com", "nosreffej4891");
        Customer newCustomer = new Customer(idCounter, "Jeffery", "Jefferson", "jeffjeff@gmail.com");
        adminFacade.addCustomer(customer);
        customerAssertion.accept(newCustomer);
    }

    @Test
    public void updateCustomerTest() throws Exception {
        String password = "nosreffej6891";
        Customer customer = new Customer("Jennifer", "Jefferson", "jennjeff@gmail.com", password);
        adminFacade.addCustomer(customer);
        customer = adminFacade.readCustomer(idCounter);
        customer.setPassword(password);
        customer.setLastName("Robinson");
        customer.setEmail("jennrobinson@gmail.com");
        adminFacade.updateCustomer(customer);
        customerAssertion.accept(customer);
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        Customer customer = new Customer("Jennifer", "Jefferson", "jennjeff@gmail.com", "nosreffej6891");
        adminFacade.addCustomer(customer);
        assertTrue(CustomerDBDAO.getInstance().isCustomerExist("jennjeff@gmail.com"));
        adminFacade.deleteCustomer(idCounter);
        assertFalse(CustomerDBDAO.getInstance().isCustomerExist("jennjeff@gmail.com"));
    }

    @Test
    public void readAllCustomersTest() throws Exception {
        Customer customer1 = new Customer("Jeffery", "Jefferson", "jeffjeff@gmail.com", "nosreffej4891");
        Customer customer2 = new Customer("Jennifer", "Jefferson", "jennjeff@gmail.com", "nosreffej6891");
        Customer customer3 = new Customer("Fred", "Friedman", "freddytheman@gmail.com", "19fredderf89");
        List<Customer> customerList = List.of(customer1, customer2, customer3);
        customerList.forEach(customer -> customerCreation.accept(customer));
        List<Customer> newCustomerList = adminFacade.readAllCustomers();
        newCustomerList.forEach(customer -> customerAssertion.accept(customer));
    }

    @Test
    public void printAllCustomersTest() throws Exception {
        Customer customer1 = new Customer("Jeffery", "Jefferson", "jeffjeff@gmail.com", "nosreffej4891");
        Customer customer2 = new Customer("Jennifer", "Jefferson", "jennjeff@gmail.com", "nosreffej6891");
        Customer customer3 = new Customer("Fred", "Friedman", "freddytheman@gmail.com", "19fredderf89");
        List<Customer> customerList = List.of(customer1, customer2, customer3);
        customerList.forEach(customer -> customerCreation.accept(customer));
        TablePrinterUtil.print(adminFacade.readAllCustomers());
    }
}

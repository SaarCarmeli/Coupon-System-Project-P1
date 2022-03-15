package Tests.LoginTests;

import Beans.Company;
import Beans.Customer;
import Beans.Util.TablePrinterUtil;
import DB.DatabaseInitializer;
import DB.Util.DBTools;
import DBDAO.CompanyDBDAO;
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

    public static Consumer<Customer> customerAssertion = customer -> {
        try {
            assertEquals(customer.getId(), adminFacade.readCustomer(idCounter).getId());
            assertEquals(customer.getFirstName(), adminFacade.readCustomer(idCounter).getFirstName());
            assertEquals(customer.getLastName(), adminFacade.readCustomer(idCounter).getLastName());
            assertEquals(customer.getEmail(), adminFacade.readCustomer(idCounter).getEmail());
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
        System.out.println("Dropped schema: " + DBTools.runQuery("DROP DATABASE `coupon_project`"));
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
        assertTrue(CompanyDBDAO.getInstance().isCompanyExist("Motti Hovalot", "Motti@Mmail.com"));
        adminFacade.deleteCompany(idCounter);
        assertFalse(CompanyDBDAO.getInstance().isCompanyExist("Motti Hovalot", "Motti@Mmail.com"));
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
}

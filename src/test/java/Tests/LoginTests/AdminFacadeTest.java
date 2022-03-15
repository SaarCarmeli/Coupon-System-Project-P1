package Tests.LoginTests;

import Beans.Company;
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
        Company newCompany = new Company(1, "Itzik hooBanav", "itzB@itzmail.com");
        adminFacade.addCompany(company);
        companyAssertion.accept(newCompany);
    }

    @Test
    public void updateCompanyTest() throws Exception {
        Company company = new Company("Tzmigey Ferdinand von Schlauffer", "Tzmigimail@TzamigBusinessMail.com", "12345678");
        adminFacade.addCompany(company);
        company = adminFacade.readCompany(1);
        company.setEmail("businessMail@TzamigMail.com");
        company.setPassword("87654321");
        adminFacade.updateCompany(company);
        companyAssertion.accept(company);
    }

    @Test
    public void deleteCompanyTest() throws Exception {
        Company company = new Company("Motti Hovalot", "Motti@Mmail.com", "abc123");
        adminFacade.addCompany(company);
        assertTrue(CompanyDBDAO.getInstance().isCompanyExist("Motti Hovalot", "Motti@Mmail.com"));
        adminFacade.deleteCompany(1);
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
}

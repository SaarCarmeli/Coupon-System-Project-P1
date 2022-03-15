package Tests.LoginTests;

import Beans.Company;
import DB.DatabaseInitializer;
import DB.Util.DBTools;
import DBDAO.CompanyDBDAO;
import Facades.AdminFacade;
import LoginManager.ClientType;
import LoginManager.LoginManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AdminFacadeTest {
    public static AdminFacade adminFacade;

    @Before
    public void initiation() {
        DatabaseInitializer.createTables();
        adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR"));
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
        assertEquals(newCompany.getId(), adminFacade.readCompany(1).getId());
        assertEquals(newCompany.getName(), adminFacade.readCompany(1).getName());
        assertEquals(newCompany.getEmail(), adminFacade.readCompany(1).getEmail());
    }

    @Test
    public void updateCompanyTest() throws Exception {
        Company company = new Company("Tzmigey Ferdinand von Schlauffer", "Tzmigimail@TzamigBusinessMail.com", "12345678");
        Company newCompany;
        adminFacade.addCompany(company);
        company = adminFacade.readCompany(1);
        company.setEmail("businessMail@TzamigMail.com");
        company.setPassword("87654321");
        adminFacade.updateCompany(company);
        newCompany = adminFacade.readCompany(1);
        assertEquals(company.getId(), newCompany.getId());
        assertEquals(company.getName(), newCompany.getName());
        assertEquals(company.getEmail(), newCompany.getEmail());
    }

    @Test
    public void deleteCompanyTest() throws Exception {
        Company company = new Company("Motti Hovalot","Motti@Mmail.com","abc123");
        adminFacade.addCompany(company);
        assertTrue(CompanyDBDAO.getInstance().isCompanyExist("Motti Hovalot", "Motti@Mmail.com"));
        adminFacade.deleteCompany(1);
        assertFalse(CompanyDBDAO.getInstance().isCompanyExist("Motti Hovalot", "Motti@Mmail.com"));
    }
}

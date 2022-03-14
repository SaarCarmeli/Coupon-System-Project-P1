package Tests.LoginTests;

import Beans.Company;
import DB.DatabaseInitializer;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Facades.AdminFacade;
import LoginManager.ClientType;
import LoginManager.LoginManager;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdminFacadeTest {
    public static AdminFacade adminFacade;

    @BeforeClass
    public static void initiation() {
        DatabaseInitializer.createTables();
        adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR"));
    }

    @Test
    public void addCompanyTest() throws Exception {
        Company company = new Company("Itzik hooBanav", "itzB@itzmail.com", "19itzbanav_50");
        try {
            adminFacade.addCompany(company);
            assertEquals(new Company(1, "Itzik hooBanav", "itzB@itzmail.com"), adminFacade.readCompany(1));
        } catch (EntityAlreadyExistException | EntityCrudException e) {
            throw new Exception(e.getMessage());
        }
    }
}

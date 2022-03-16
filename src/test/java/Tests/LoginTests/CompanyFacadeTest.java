package Tests.LoginTests;

import DB.DatabaseInitializer;
import DB.Util.DBTools;
import Facades.AdminFacade;
import LoginManager.ClientType;
import LoginManager.LoginManager;
import org.junit.After;
import org.junit.Before;

import java.sql.SQLException;

public class CompanyFacadeTest {
    public static AdminFacade adminFacade;
    public static int idCounter = 1;

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
}

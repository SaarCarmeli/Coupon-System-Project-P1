package Tests.LoginTests;

import Facades.AdminFacade;
import LoginManager.LoginManager;
import LoginManager.ClientType;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginManagerTest {
    public static LoginManager loginManager;

    @BeforeClass
    public static void initiation() {
        loginManager = LoginManager.getInstance();
    }

    @Test
    public void adminLoginTest() {
        assertEquals(AdminFacade.class, loginManager.login("admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR")).getClass());
    }
}

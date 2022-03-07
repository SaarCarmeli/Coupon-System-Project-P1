package LoginManager;

import DB.Util.DBManager;
import DB.Util.DBTools;
import Exceptions.EntityCrudException;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {
    private static LoginManager instance = null;

    //c'tor
    private LoginManager() {
    }

    //getting instance.
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public Object login(String email, String password, ClientType clientType) {

        switch (clientType) {
            case ADMINISTRATOR:
                if (email.equals("admin@admin.com") && password.equals("admin")) {
                    return new AdminFacade();
                }
            case COMPANY:
                ResultSet companyResult;
                Map<Integer, Object> companyParams = new HashMap<>();
                companyParams.put(1, email);
                companyParams.put(2, password);
                try {
                    companyResult = DBTools.runQueryForResult(DBManager.COMPANY_LOGGING, companyParams);
                    companyResult.next();
                    return new CompanyFacade(companyResult.getInt(0));
                } catch (SQLException | EntityCrudException e) {
                    System.out.println("Error! Login failed!");
                }
            case CUSTOMER:
                ResultSet customerResult;
                Map<Integer, Object> customerParams = new HashMap<>();
                customerParams.put(1, email);
                customerParams.put(2, password);
                try {
                    customerResult = DBTools.runQueryForResult(DBManager.CUSTOMER_LOGGING, customerParams);
                    customerResult.next();
                    return new CustomerFacade(customerResult.getInt(0));
                } catch (SQLException | EntityCrudException e) {
                    System.out.println("Error! Login failed!");
                }
        }
        return null;
    }
}






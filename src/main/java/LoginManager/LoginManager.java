package LoginManager;

import DB.DBManager;
import DB.DBTools;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {
    private static LoginManager instance = null;

    private LoginManager() {
    }

    /**
     * Static method for retrieving and/or initiating an instance of LoginManager.
     *
     * @return LoginManager instance
     */
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

    /**
     * Method for logging into the client facade. Checks the database for a match between client input and an existing record.
     *
     * @param email      Client email
     * @param password   Client password
     * @param clientType Client type is either ADMINISTRATOR, COMPANY or CUSTOMER
     * @return AdminFacade if client is administrator, CompanyFacade if client is a Company, CustomerFacade if client is
     * a Customer or null if there is no match with input
     */
    public Object login(String email, String password, ClientType clientType) {
        switch (clientType) {
            case ADMINISTRATOR:
                if (email.equals("admin@admin.com") && password.equals("admin")) {
                    return new AdminFacade();
                }
                break;
            case COMPANY:
                ResultSet companyResult;
                Map<Integer, Object> companyParams = new HashMap<>();
                companyParams.put(1, email);
                companyParams.put(2, password);
                try {
                    companyResult = DBTools.runQueryForResult(DBManager.COMPANY_LOGGING, companyParams);
                    assert companyResult != null;
                    companyResult.next();
                    return new CompanyFacade(companyResult.getInt(1));
                } catch (SQLException e) {
                    System.out.println("Error! Login failed!");
                    break;
                }
            case CUSTOMER:
                ResultSet customerResult;
                Map<Integer, Object> customerParams = new HashMap<>();
                customerParams.put(1, email);
                customerParams.put(2, password);
                try {
                    customerResult = DBTools.runQueryForResult(DBManager.CUSTOMER_LOGGING, customerParams);
                    assert customerResult != null;
                    customerResult.next();
                    return new CustomerFacade(customerResult.getInt(1));
                } catch (SQLException e) {
                    System.out.println("Error! Login failed!");
                    break;
                }
        }
        System.out.println("Login failed! No match found with input!");
        return null;
    }
}






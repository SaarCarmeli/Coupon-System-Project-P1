package LoginManager;

import DB.Util.DBManager;
import DB.Util.DBTools;
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
                       // break; not necessary
                    }
                case COMPANY:
                    int companyCounter;
                    ResultSet companyResult;
                    Map<Integer, Object> companyParams = new HashMap<>();
                    companyParams.put(1, email);
                    companyParams.put(2, password);
                    try {
                        companyResult = DBTools.runQueryForResult(DBManager.COMPANY_LOGGING, companyParams);
                        assert companyResult != null;
                        companyResult.next();
                        companyCounter = companyResult.getInt(1);
                        if (companyCounter == 1) {
                            return new CompanyFacade();
                            // break; not necessary
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                case CUSTOMER:
                    int customerCounter;
                    ResultSet customerResult;
                    Map<Integer, Object> customerParams = new HashMap<>();
                    customerParams.put(1, email);
                    customerParams.put(2, password);
                    try {
                        customerResult = DBTools.runQueryForResult(DBManager.CUSTOMER_LOGGING, customerParams);
                        assert customerResult != null;
                        customerResult.next();
                        customerCounter = customerResult.getInt(1);
                        if (customerCounter == 1) {
                            return new CustomerFacade();
                            // break; not necessary
                        }
                    } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        return null;
    }
}






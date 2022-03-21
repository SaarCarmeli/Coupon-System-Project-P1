package Tests.FacadeTests;

import DB.Util.DBManager;
import DB.Util.DBTools;
import Exceptions.CrudOperation;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class TestDBMethods {
    public static boolean isCompanyExistById(int companyId) throws EntityCrudException {
        int counter;
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        try {
            result = DBTools.runQueryForResult(DBManager.COUNT_COMPANY_BY_ID, params);
            assert result != null;
            result.next();
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.COUNT);
        }
    }

    public static boolean isCustomerExistById(int customerId) throws EntityCrudException {
        int counter;
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        try {
            result = DBTools.runQueryForResult(DBManager.COUNT_CUSTOMER_BY_ID, params);
            assert result != null;
            result.next();
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.COUNT);
        }
    }

    public static boolean isCouponExistById(int couponId) throws EntityCrudException {
        int counter;
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        try {
            result = DBTools.runQueryForResult(DBManager.COUNT_COUPON_BY_ID, params);
            assert result != null;
            result.next();
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.COUNT);
        }
    }

    public static boolean isPurchaseExistById(int customerId, int couponId) throws EntityCrudException {
        int counter;
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1,customerId);
        params.put(2, couponId);
        try {
            result = DBTools.runQueryForResult(DBManager.COUNT_PURCHASE_BY_IDS, params);
            assert result != null;
            result.next();
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.COUNT);
        }
    }
}

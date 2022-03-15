package DB;

import DB.Util.DBManager;
import DB.Util.DBTools;
import Exceptions.DBException;
import Exceptions.DBInitException;

import java.sql.SQLException;


public class DatabaseInitializer {
    public DatabaseInitializer() {
    }

    public static void createTables() {
        try {
            createSchema();
            createCompaniesTable();
            createCustomersTable();
            createCouponsTable();
            createCustomerToCouponTable();
        } catch (DBInitException | DBException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createSchema() throws DBInitException {
        try {
            System.out.println("Created schema: " + DBTools.runQuery(DBManager.CREATE_SCHEMA));
        } catch (SQLException e) {
            throw new DBInitException();
        }
    }

    public static void createCompaniesTable() throws DBException {
        try {
            System.out.println("Created \"companies\" table: " + DBTools.runQuery(DBManager.CREATE_TABLE_COMPANIES));
        } catch (SQLException e) {
            throw new DBException("Failed to create \"companies\" table");
        }
    }

    public static void createCustomersTable() throws DBException {
        try {
            System.out.println("Created \"customers\" table: " + DBTools.runQuery(DBManager.CREATE_TABLE_CUSTOMERS));
        } catch (SQLException e) {
            throw new DBException("Failed to create \"customers\" table");
        }
    }

    public static void createCouponsTable() throws DBException {
        try {
            System.out.println("Created \"coupons\" table: " + DBTools.runQuery(DBManager.CREATE_TABLE_COUPONS));
        } catch (SQLException e) {
            throw new DBException("Failed to create \"coupons\" table");
        }
    }

    public static void createCustomerToCouponTable() throws DBException {
        try {
            System.out.println("Created \"customer_to_coupon\" table: " + DBTools.runQuery(DBManager.CREATE_TABLE_CUSTOMER_TO_COUPON));
        } catch (SQLException e) {
            throw new DBException("Failed to create \"customer_to_coupon\" table");
        }
    }
}
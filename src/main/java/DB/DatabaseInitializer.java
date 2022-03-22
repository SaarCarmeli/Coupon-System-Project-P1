package DB;

import Exceptions.DBException;
import Exceptions.DBInitException;

import java.sql.SQLException;

/**
 * Contains all methods necessary for database initialization.
 */
public class DatabaseInitializer {
    /**
     * Constructs new DatabaseInitializer
     */
    public DatabaseInitializer() {
    }

    /**
     * Static method for creating MySQL database and Companies, Customers, Coupons and Customer_To_Coupons tables.
     */
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

    /**
     * Static method for creating MySQL schema (database).
     *
     * @throws DBInitException Thrown if failed to create database
     */
    private static void createSchema() throws DBInitException {
        try {
            System.out.println("Created schema: " + DBTools.runQuery(DBManager.CREATE_SCHEMA));
        } catch (SQLException e) {
            throw new DBInitException();
        }
    }

    /**
     * Static method for creating "companies" table in MySQL database.
     *
     * @throws DBException Thrown if failed to create table
     */
    public static void createCompaniesTable() throws DBException {
        try {
            System.out.println("Created \"companies\" table: " + DBTools.runQuery(DBManager.CREATE_TABLE_COMPANIES));
        } catch (SQLException e) {
            throw new DBException("Failed to create \"companies\" table");
        }
    }

    /**
     * Static method for creating "customers" table in MySQL database.
     *
     * @throws DBException Thrown if failed to create table
     */
    public static void createCustomersTable() throws DBException {
        try {
            System.out.println("Created \"customers\" table: " + DBTools.runQuery(DBManager.CREATE_TABLE_CUSTOMERS));
        } catch (SQLException e) {
            throw new DBException("Failed to create \"customers\" table");
        }
    }

    /**
     * Static method for creating "coupons" table in MySQL database.
     *
     * @throws DBException Thrown if failed to create table
     */
    public static void createCouponsTable() throws DBException {
        try {
            System.out.println("Created \"coupons\" table: " + DBTools.runQuery(DBManager.CREATE_TABLE_COUPONS));
        } catch (SQLException e) {
            throw new DBException("Failed to create \"coupons\" table");
        }
    }

    /**
     * Static method for creating "customer_to_coupon" table in MySQL database.
     *
     * @throws DBException Thrown if failed to create table
     */
    public static void createCustomerToCouponTable() throws DBException {
        try {
            System.out.println("Created \"customer_to_coupon\" table: " + DBTools.runQuery(DBManager.CREATE_TABLE_CUSTOMER_TO_COUPON));
        } catch (SQLException e) {
            throw new DBException("Failed to create \"customer_to_coupon\" table");
        }
    }
}
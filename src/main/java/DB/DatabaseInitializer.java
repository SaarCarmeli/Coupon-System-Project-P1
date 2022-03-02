package DB;

import DB.Util.DBManager;
import DB.Util.DBTools;
import Exceptions.DBException;
import Exceptions.DBInitException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    private static final Connection connection; //todo delete replace with runQuery

    static {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (InterruptedException | SQLException e) {
            throw new DBInitException();
            //todo delete
        }
    }

    public static void createTables() throws DBException, SQLException {
        try {
            createSchema();
            createCategoriesTable();
            createCompaniesTable();
            createCustomersTable();
            createCouponsTable();
            createCustomerToCouponTable();
        } catch (DBInitException e) {
            e.getMessage();
        }
        ConnectionPool.getInstance().returnConnection(connection);
    }

    private static void createSchema() throws DBInitException {
        try {
            System.out.println("Created schema: " + DBTools.runQuery(DBManager.CREATE_SCHEMA));
        } catch (SQLException e) {
            throw new DBInitException();
        }
    }

    public static void createCategoriesTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `categories` (" +
                "  `id` bigint NOT NULL AUTO_INCREMENT," +
                "  `name` enum('FOOD','ELECTRICITY','RESTAURANT','VACATION') NOT NULL," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create categories table"); //todo how to integrate new method with existing custom exceptions?
        }
    }

    public static void createCompaniesTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `companies` (" +
                "  `id` bigint NOT NULL AUTO_INCREMENT," +
                "  `name` varchar(45) NOT NULL," +
                "  `email` varchar(45) NOT NULL," +
                "  `password` bigint NOT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE KEY `name_UNIQUE` (`name`)," +
                "  UNIQUE KEY `email_UNIQUE` (`email`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create companies table");
        }
    }

    public static void createCustomersTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `customers` (" +
                "  `id` bigint NOT NULL AUTO_INCREMENT," +
                "  `first_name` varchar(45) NOT NULL," +
                "  `last_name` varchar(45) NOT NULL," +
                "  `email` varchar(45) NOT NULL," +
                "  `password` bigint NOT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE KEY `email_UNIQUE` (`email`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create customers table");
        }
    }

    public static void createCouponsTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `coupons` (" +
                "  `id` bigint NOT NULL AUTO_INCREMENT," +
                "  `company_id` bigint NOT NULL," +
                "  `category` varchar(45) NOT NULL," +
                "  `title` varchar(45) NOT NULL," +
                "  `description` varchar(45) DEFAULT NULL," +
                "  `start_date` date NOT NULL," +
                "  `end_date` date NOT NULL," +
                "  `amount` int NOT NULL," +
                "  `price` varchar(45) NOT NULL," +
                "  `image` varchar(45) DEFAULT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create coupons table");
        }
    }

    public static void createCustomerToCouponTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `customer_to_coupon` (" +
                "  `customer_id` bigint NOT NULL," +
                "  `coupon_id` bigint NOT NULL," +
                "  KEY `customer.id_idx` (`customer_id`)," +
                "  KEY `coupon.id_idx` (`coupon_id`)," +
                "  CONSTRAINT `coupon.id` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`)," +
                "  CONSTRAINT `customer.id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create customers and coupons table");
        }
    }
}
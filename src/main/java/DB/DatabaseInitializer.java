package DB;

import Exceptions.DBInitError;
import Exceptions.DBException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    private static final Connection connection;

    static {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (InterruptedException | SQLException e) {
            throw new DBInitError();
        }
    }

    public static void createTables() throws DBException, SQLException {
        createCategoriesTable();
        createCompaniesTable();
        createCustomersTable();
        createCouponsTable();
        createCustomerToCouponTable();
        ConnectionPool.getInstance().returnConnection(connection);
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
            throw new DBException("Failed to create categories table");
        }
    }

    public static void createCompaniesTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `companies` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `email` varchar(45) NOT NULL,\n" +
                "  `password` bigint NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `name_UNIQUE` (`name`),\n" +
                "  UNIQUE KEY `email_UNIQUE` (`email`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create companies table");
        }
    }

    public static void createCustomersTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `customers` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                "  `first_name` varchar(45) NOT NULL,\n" +
                "  `last_name` varchar(45) NOT NULL,\n" +
                "  `email` varchar(45) NOT NULL,\n" +
                "  `password` bigint NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `email_UNIQUE` (`email`)\n" +
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
                "  `category_id` bigint NOT NULL," +
                "  `title` varchar(45) NOT NULL," +
                "  `description` varchar(45) DEFAULT NULL," +
                "  `start_date` date NOT NULL," +
                "  `end_date` date NOT NULL," +
                "  `amount` int NOT NULL," +
                "  `price` varchar(45) NOT NULL," +
                "  `image` varchar(45) DEFAULT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  CONSTRAINT `category.id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),\n" +
                "  CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Failed to create coupons table");
        }
    }

    public static void createCustomerToCouponTable() throws DBException {
        String sql = "CREATE TABLE IF NOT EXISTS `customer_to_coupon` (\n" +
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

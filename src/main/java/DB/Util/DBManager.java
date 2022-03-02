package DB.Util;

public class DBManager {
    // User info:
    public static final String SQL_URL = "jdbc:mysql://localhost:3306/coupon_project";
    public static final String SQL_USER = "root";
    public static final String SQL_PASS = System.getenv("SQL_PASSWORD");

    // Schema Creation:
    public static final String CREATE_SCHEMA = "CREATE SCHEMA IF NOT EXISTS `coupon_project`";

    // Table Creation:
    public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS `categories` (" +
            "  `id` bigint NOT NULL AUTO_INCREMENT," +
            "  `name` enum('FOOD','ELECTRICITY','RESTAURANT','VACATION') NOT NULL," +
            "  PRIMARY KEY (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"; //todo

    public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS `companies` (" +
            "  `id` bigint NOT NULL AUTO_INCREMENT," +
            "  `name` varchar(45) NOT NULL," +
            "  `email` varchar(45) NOT NULL," +
            "  `password` bigint NOT NULL," + // todo should it be in the database???
            "  PRIMARY KEY (`id`)," +
            "  UNIQUE KEY `name_UNIQUE` (`name`)," +
            "  UNIQUE KEY `email_UNIQUE` (`email`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";

    public static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS `customers` (" +
            "  `id` bigint NOT NULL AUTO_INCREMENT," +
            "  `first_name` varchar(45) NOT NULL," +
            "  `last_name` varchar(45) NOT NULL," +
            "  `email` varchar(45) NOT NULL," +
            "  `password` bigint NOT NULL," +
            "  PRIMARY KEY (`id`)," +
            "  UNIQUE KEY `email_UNIQUE` (`email`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";

    public static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS `coupons` (" +
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
            "  CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)" +//todo delete on action
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";

    public static final String CREATE_TABLE_CUSTOMER_TO_COUPON = "CREATE TABLE IF NOT EXISTS `customer_to_coupon` (" +
            "  `customer_id` bigint NOT NULL," +
            "  `coupon_id` bigint NOT NULL," +
            "  KEY `customer.id_idx` (`customer_id`)," +
            "  KEY `coupon.id_idx` (`coupon_id`)," +
            "  CONSTRAINT `coupon.id` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`)," +
            "  CONSTRAINT `customer.id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";

    // Company CRUD:
    public static final String CREATE_COMPANY = "INSERT INTO companies (name, email, password) VALUES(?, ?, ?)";
    public static final String READ_COMPANY_BY_ID = "SELECT * FROM companies WHERE id = ?";
    public static final String READ_ALL_COMPANIES = "SELECT * FROM companies";
    public static final String UPDATE_COMPANY = "UPDATE companies SET name = ?, email = ?, password = ?";
}

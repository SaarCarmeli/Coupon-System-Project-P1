package DB.Util;

public class DBManager {
    // User info:
    public static final String SQL_URL = "jdbc:mysql://localhost:3306/";
    public static final String SQL_USER = "root";
    public static final String SQL_PASS = "12345678";

    // Schema Creation:
    public static final String CREATE_SCHEMA = "CREATE SCHEMA IF NOT EXISTS `coupon_project`";

    // Table Creation:
    public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS `coupon_project`.`companies` (" +
            "  `id` BIGINT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";

    public static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS `coupon_project`.`customers` (" +
            "  `customer_id` BIGINT NOT NULL AUTO_INCREMENT," +
            "  `first_name` VARCHAR(45) NOT NULL," +
            "  `last_name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`customer_id`));";

    public static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS `coupon_project`.`coupons` (" +
            "  `coupon_id` BIGINT NOT NULL AUTO_INCREMENT," +
            "  `company_id` BIGINT NOT NULL," +
            "  `category` VARCHAR(45) NOT NULL," +
            "  `title` VARCHAR(45) NOT NULL," +
            "  `description` VARCHAR(45) NOT NULL," +
            "  `start_date` DATE NOT NULL," +
            "  `end_date` VARCHAR(45) NOT NULL," +
            "  `amount` INT NOT NULL," +
            "  `price` DOUBLE NOT NULL," +
            "  `image` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`coupon_id`)," +
            "  CONSTRAINT `company_id`" +
            "    FOREIGN KEY (`company_id`)" +
            "    REFERENCES `coupon_project`.`companies` (`id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE);";

    public static final String CREATE_TABLE_CUSTOMER_TO_COUPON = "CREATE TABLE IF NOT EXISTS `coupon_project`.`customer_to_coupon` (" +
            "  `customer_id` BIGINT NOT NULL," +
            "  `coupon_id` BIGINT NOT NULL," +
            "  INDEX `customer_id_idx` (`customer_id` ASC) VISIBLE," +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE," +
            "  CONSTRAINT `customer_id`" +
            "    FOREIGN KEY (`customer_id`)" +
            "    REFERENCES `coupon_project`.`customers` (`customer_id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE," +
            "  CONSTRAINT `coupon_id`" +
            "    FOREIGN KEY (`coupon_id`)" +
            "    REFERENCES `coupon_project`.`coupons` (`coupon_id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE);";

    // Company CRUD:
    public static final String CREATE_COMPANY = "INSERT INTO `coupon_project`.`companies` (name, email, password) VALUES(?, ?, ?)";
    public static final String READ_COMPANY_BY_ID = "SELECT id, name, email FROM `coupon_project`.`companies` WHERE id = ?";
    public static final String READ_ALL_COMPANIES = "SELECT id, name, email FROM `coupon_project`.`companies`";
    public static final String UPDATE_COMPANY_BY_ID = "UPDATE `coupon_project`.`companies` SET email = ?, password = ? WHERE id = ?";
    public static final String DELETE_COMPANY_BY_ID = "DELETE FROM `coupon_project`.`companies` WHERE id = ?";
    public static final String COUNT_COMPANIES_BY_NAME_OR_EMAIL = "SELECT COUNT(*) FROM `coupon_project`.`companies` WHERE name = ? OR email = ?";

    // Coupon CRUD:
    public static final String CREATE_COUPON = "INSERT INTO `coupon_project`.`coupons` (company_id, amount, price, category, title, description, image, start_date, end_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";// todo check match with map
    public static final String ADD_COUPON_PURCHASE = "INSERT INTO `coupon_project`.`customer_to_coupon` (customer_id, coupon_id) VALUES (?, ?)";
    public static final String READ_COUPON_BY_ID = "SELECT * FROM `coupon_project`.`coupons` WHERE coupon_id = ?";
    public static final String READ_ALL_COUPONS = "SELECT * FROM `coupon_project`.`coupons`";
    public static final String READ_COUPONS_BY_CUSTOMER_ID = "SELECT coupon_id, company_id, amount, price, category, title, description, image, start_date, end_date FROM `coupon_project`.`customer_to_coupon`, `coupon_project`.`coupons` WHERE customer_id = ?"; // todo check in mysql workbench
    public static final String READ_COUPONS_BY_CUSTOMER_ID_AND_MAX_PRICE = "SELECT coupon_id, company_id, amount, price, category, title, description, image, start_date, end_date FROM `coupon_project`.`customer_to_coupon`, `coupon_project`.`coupons` WHERE customer_id = ? AND price <= ?";// todo check in mysql workbench
    public static final String READ_COUPONS_BY_CUSTOMER_ID_AND_CATEGORY = "SELECT coupon_id, company_id, amount, price, category, title, description, image, start_date, end_date FROM `coupon_project`.`customer_to_coupon`, `coupon_project`.`coupons` WHERE customer_id = ? AND category = ?";// todo check in mysql workbench
    public static final String READ_COUPONS_BY_COMPANY_ID = "SELECT * FROM `coupon_project`.`coupons` WHERE company_id = ?";
    public static final String READ_COUPONS_BY_COMPANY_ID_AND_MAX_PRICE = "SELECT * FROM `coupon_project`.`coupons` WHERE company_id = ? AND price <= ?";
    public static final String READ_COUPONS_BY_COMPANY_ID_AND_CATEGORY = "SELECT * FROM `coupon_project`.`coupons` WHERE company_id = ? AND category = ?";
    public static final String UPDATE_COUPON_BY_ID = "UPDATE `coupon_project`.`coupons` SET title = ?, category = ? ,amount = ? , description = ? ,price = ? ,image = ? ,end_date = ? WHERE coupon_id = ?";
    public static final String DELETE_COUPON_BY_ID = "DELETE FROM `coupon_project`.`coupons` WHERE coupon_id = ?";
    public static final String DELETE_COUPON_BY_END_DATE = "DELETE FROM `coupon_project`.`coupons` WHERE end_date < ?";
    public static final String COUNT_COUPONS_BY_COMPANY_ID_AND_TITLE = "SELECT COUNT(*) FROM `coupon_project`.`coupons` WHERE company_id = ? AND title = ?";
    public static final String COUNT_COUPONS_BY_CUSTOMER_ID = "SELECT COUNT(*) FROM `coupon_project`.`customer_to_coupon` WHERE coupon_id = ? AND customer_id = ?"; // todo check that works

    // Customer CRUD:
    public static final String CREATE_CUSTOMER = "INSERT INTO `coupon_project`.`customers` (first_name, last_name, email, password) VALUES(?, ?, ?, ?)";
    public static final String READ_CUSTOMER_BY_ID = "SELECT customer_id, first_name, last_name, email FROM `coupon_project`.`customers` WHERE customer_id = ?";
    public static final String READ_ALL_CUSTOMERS = "SELECT customer_id, first_name, last_name, email FROM `coupon_project`.`customers`";
    public static final String UPDATE_CUSTOMER_BY_ID = "UPDATE `coupon_project`.`customers` SET first_name = ?,last_name = ?, email = ?, password = ? WHERE customer_id = ?";
    public static final String DELETE_CUSTOMER_BY_ID = "DELETE FROM `coupon_project`.`customers` WHERE customer_id = ?";
    public static final String COUNT_CUSTOMERS_BY_EMAIL = "SELECT COUNT(*) FROM `coupon_project`.`customers` WHERE email = ?";

    // Login:
    public static final String COMPANY_LOGGING = "SELECT id FROM `coupon_project`.`companies` WHERE email = ? AND password = ?";
    public static final String CUSTOMER_LOGGING = "SELECT id FROM `coupon_project`.`customers` WHERE email = ? AND password = ?";

    // Test:
    public static final String DROP_SCHEMA = "DROP DATABASE `coupon_project`";

    public static final String COUNT_COMPANY_BY_ID = "SELECT COUNT(*) FROM `coupon_project`.`companies` WHERE id = ?";
    public static final String COUNT_CUSTOMER_BY_ID = "SELECT COUNT(*) FROM `coupon_project`.`customers` WHERE customer_id = ?";
    public static final String COUNT_COUPON_BY_ID = "SELECT COUNT(*) FROM `coupon_project`.`coupons` WHERE coupon_id = ?";
}

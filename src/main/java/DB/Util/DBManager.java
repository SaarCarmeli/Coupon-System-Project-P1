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
            "   `id` BIGINT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`))"; //todo

    public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS `companies` (" +
            "  `id` BIGINT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," + // todo consider if should be in database?
            "  PRIMARY KEY (`id`));";

    public static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS `customers` (" +
            "`id` BIGINT NOT NULL AUTO_INCREMENT," +
            "  `first_name` VARCHAR(45) NOT NULL," +
            "  `last_name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," + // todo consider if should be in database?
            "  PRIMARY KEY (`id`));";

    public static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS `coupons` (" +
            "   `id` BIGINT NOT NULL AUTO_INCREMENT," +
            "  `company_id` BIGINT NOT NULL," +
            "  `category_id` BIGINT NOT NULL," +
            "  `title` VARCHAR(45) NOT NULL," +
            "  `description` VARCHAR(45) NOT NULL," +
            "  `start_date` DATE NOT NULL," +
            "  `end_date` VARCHAR(45) NOT NULL," +
            "  `amount` INT NOT NULL," +
            "  `price` DOUBLE NOT NULL," + // todo change in methods?
            "  `image` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`)," +
            "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE," +
            "  CONSTRAINT `company_id`" +
            "    FOREIGN KEY (`company_id`)" +
            "    REFERENCES `coupon_project`.`companies` (`id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE," +
            "  CONSTRAINT `category_id`" +
            "    FOREIGN KEY (`category_id`)" +
            "    REFERENCES `coupon_project`.`categories` (`id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE);"; // todo maybe add ON CREATE CASCADE?

    public static final String CREATE_TABLE_CUSTOMER_TO_COUPON = "CREATE TABLE IF NOT EXISTS `customer_to_coupon` (" +
            "  `customer_id` BIGINT NOT NULL," +
            "  `coupon_id` BIGINT NOT NULL," +
            "  INDEX `customer_id_idx` (`customer_id` ASC) VISIBLE," +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE," +
            "  CONSTRAINT `customer_id`" +
            "    FOREIGN KEY (`customer_id`)" +
            "    REFERENCES `coupon_project`.`customers` (`id`)" +
            "    ON DELETE CASCADE" + //todo:check maybe it's should be NO ACTION
            "    ON UPDATE CASCADE," +
            "  CONSTRAINT `coupon_id`" +
            "    FOREIGN KEY (`coupon_id`)" +
            "    REFERENCES `coupon_project`.`coupons` (`id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE);";

    // Company CRUD:
    public static final String CREATE_COMPANY = "INSERT INTO companies (name, email, password) VALUES(?, ?, ?)";
    public static final String READ_COMPANY_BY_ID = "SELECT * FROM companies WHERE id = ?";
    public static final String READ_ALL_COMPANIES = "SELECT * FROM companies";
    public static final String UPDATE_COMPANY = "UPDATE companies SET name = ?, email = ?, password = ?"; // todo "where id = ?" ???
    public static final String DELETE_COMPANY_BY_ID = "DELETE FROM companies WHERE id = ?";
    public static final String COUNT_COMPANIES_BY_NAME_OR_EMAIL = "SELECT count(*) FROM companies WHERE name = ? OR email = ?";

    // Coupon CRUD:
    public static final String CREATE_COUPON = "INSERT INTO coupons (title, company_id, start_date, end_date, amount, category, description, price, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String READ_COUPON_BY_ID = "SELECT * FROM coupons WHERE id = ?";
    public static final String READ_ALL_COUPONS = "SELECT * FROM coupons";

    // Customer CRUD:
    public static final String CREATE_CUSTOMER = "INSERT INTO customers (first_name, last_name, email, password) VALUES(?, ?, ?, ?)";
    public static final String READ_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id = ?";
    public static final String READ_ALL_CUSTOMERS = "SELECT * FROM customers";
    public static final String UPDATE_CUSTOMER = "UPDATE customers SET first_name = ?,last_name = ?, email = ?, password = ?"; // todo "where id = ?" ???
    public static final String DELETE_CUSTOMER_BY_ID = "DELETE FROM customers WHERE id = ?";
    public static final String COUNT_CUSTOMERS_BY_EMAIL = "SELECT count(*) FROM customers WHERE email = ?";
    public static final String READ_COUPONS_BY_CUSTOMER_ID = "SELECT * FROM customer_to_coupon WHERE customer_id = ?";
}

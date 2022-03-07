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
            "  PRIMARY KEY (`id`))";

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
            "   `coupon_id` BIGINT NOT NULL AUTO_INCREMENT," +
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
            "    ON UPDATE CASCADE);";

    public static final String CREATE_TABLE_CUSTOMER_TO_COUPON = "CREATE TABLE IF NOT EXISTS `customer_to_coupon` (" +
            "  `customer_id` BIGINT NOT NULL," +
            "  `coupon_id` BIGINT NOT NULL," +
            "  INDEX `customer_id_idx` (`customer_id` ASC) VISIBLE," +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE," +
            "  CONSTRAINT `customer_id`" +
            "    FOREIGN KEY (`customer_id`)" +
            "    REFERENCES `coupon_project`.`customers` (`id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE," +
            "  CONSTRAINT `coupon_id`" +
            "    FOREIGN KEY (`coupon_id`)" +
            "    REFERENCES `coupon_project`.`coupons` (`id`)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE);";

    // Company CRUD:
    public static final String CREATE_COMPANY = "INSERT INTO companies (name, email, password) VALUES(?, ?, ?)";
    public static final String READ_COMPANY_BY_ID = "SELECT id, name, email FROM companies WHERE id = ?";
    public static final String READ_ALL_COMPANIES = "SELECT id, name, email FROM companies";
    public static final String UPDATE_COMPANY_BY_ID = "UPDATE companies SET name = ?, email = ?, password = ? WHERE id = ?";
    public static final String DELETE_COMPANY_BY_ID = "DELETE FROM companies WHERE id = ?";
    public static final String COUNT_COMPANIES_BY_NAME_OR_EMAIL = "SELECT count(*) FROM companies WHERE name = ? OR email = ?";

    // Coupon CRUD:
    public static final String CREATE_COUPON = "INSERT INTO coupons (company_id, amount, price, category, title, description, image, start_date, end_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";// todo check match with map
    public static final String READ_COUPON_BY_ID = "SELECT * FROM coupons WHERE coupon_id = ?";
    public static final String READ_ALL_COUPONS = "SELECT * FROM coupons";
    public static final String READ_COUPONS_BY_CUSTOMER_ID = "SELECT coupon_id, company_id, amount, price, category, title, description, image, start_date, end_date FROM customer_to_coupon, coupons WHERE customer_id = ?"; // todo check in mysql workbench
    public static final String READ_COUPONS_BY_CUSTOMER_ID_AND_MAX_PRICE = "SELECT coupon_id, company_id, amount, price, category, title, description, image, start_date, end_date FROM customer_to_coupon, coupons WHERE customer_id = ? AND price <= ?";// todo check in mysql workbench
    public static final String READ_COUPONS_BY_CUSTOMER_ID_AND_CATEGORY = "SELECT coupon_id, company_id, amount, price, category, title, description, image, start_date, end_date FROM customer_to_coupon, coupons WHERE customer_id = ? AND category = ?";// todo check in mysql workbench
    public static final String UPDATE_COUPON_BY_ID = "UPDATE coupons SET title = ?, category = ? ,amount = ? , description = ? ,price = ? ,image = ? , start_date = ? ,end_date = ? WHERE coupon_id = ?";
    public static final String DELETE_COUPON_BY_ID = "DELETE FROM coupons WHERE coupon_id = ?";
    public static final String DELETE_COUPON_BY_END_DATE = "DELETE FROM coupons WHERE end_date < ?";
    public static final String COUNT_COUPONS_BY_COMPANY_ID_AND_TITLE = "SELECT count(*) FROM coupons WHERE company_id = ? AND title = ?";

    // Customer CRUD:
    public static final String CREATE_CUSTOMER = "INSERT INTO customers (first_name, last_name, email, password) VALUES(?, ?, ?, ?)";
    public static final String READ_CUSTOMER_BY_ID = "SELECT customer_id, first_name, last_name, email FROM customers WHERE customer_id = ?";
    public static final String READ_ALL_CUSTOMERS = "SELECT customer_id, first_name, last_name, email FROM customers";
    public static final String UPDATE_CUSTOMER_BY_ID = "UPDATE customers SET first_name = ?,last_name = ?, email = ?, password = ? WHERE customer_id = ?";
    public static final String DELETE_CUSTOMER_BY_ID = "DELETE FROM customers WHERE customer_id = ?";
    public static final String COUNT_CUSTOMERS_BY_EMAIL = "SELECT count(*) FROM customers WHERE email = ?";

    //Login
    public static final String COMPANY_LOGGING = "SELECT id FROM companies WHERE email = ? AND password = ?";
    public static final String CUSTOMER_LOGGING = "SELECT id FROM customers WHERE email = ? AND password = ?";

}

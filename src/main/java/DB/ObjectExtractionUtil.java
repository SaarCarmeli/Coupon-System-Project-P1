package DB;


import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class containing static methods for extracting Company, Coupon and Customer classes from MySQL ResultSet
 */
public class ObjectExtractionUtil {

    /**
     * Static method for extracting "Company" class from MySQL ResultSet, without setting coupons list.
     * Does not extract password.
     *
     * @param result ResultSet from MySQL
     * @return new Company
     * @throws SQLException Thrown if failed to get parameters
     */
    public static Company resultSetToCompany(ResultSet result) throws SQLException {
        return new Company(
                result.getInt("id"),
                result.getString("name"),
                result.getString("email")
        );
    }

    /**
     * Static method for extracting "Company" class from MySQL ResultSet. Including setting coupons list.
     * Does not extract password.
     *
     * @param result         ResultSet from MySQL
     * @param companyCoupons List of company-issued coupons from MySQL database
     * @return new Company
     * @throws SQLException Thrown if failed to get parameters
     */
    public static Company resultSetToCompany(ResultSet result, List<Coupon> companyCoupons) throws SQLException {
        Company company = resultSetToCompany(result);
        company.setCoupons(companyCoupons);
        return company;
    }

    /**
     * Static method for extracting "Coupon" class from MySQL ResultSet.
     *
     * @param result ResultSet from MySQL
     * @return new Coupon
     * @throws SQLException Thrown if failed to get parameters
     */
    public static Coupon resultSetToCoupon(ResultSet result) throws SQLException {
        return new Coupon(
                result.getInt("coupon_id"),
                result.getInt("company_id"),
                result.getInt("amount"),
                result.getDouble("price"),
                Category.valueOf(result.getString("category")),
                result.getString("title"),
                result.getString("description"),
                result.getString("image"),
                result.getDate("start_date"),
                result.getDate("end_date")
        );
    }

    /**
     * Static method for extracting "Customer" class from MySQL ResultSet, without setting coupons list.
     * Does not extract password.
     *
     * @param result ResultSet from MySQL
     * @return new Customer
     * @throws SQLException Thrown if failed to get parameters
     */
    public static Customer resultSetToCustomer(ResultSet result) throws SQLException {
        return new Customer(
                result.getInt("customer_id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("email")
        );
    }

    /**
     * Static method for extracting "Customer" class from MySQL ResultSet. Including setting coupons list.
     * Does not extract password.
     *
     * @param result          ResultSet from MySQL
     * @param customerCoupons List of customer-owned coupons from MySQL database
     * @return new Customer
     * @throws SQLException Thrown if failed to get parameters
     */
    public static Customer resultSetToCustomer(ResultSet result, List<Coupon> customerCoupons) throws SQLException {
        Customer customer = resultSetToCustomer(result);
        customer.setCoupons(customerCoupons);
        return customer;
    }
}
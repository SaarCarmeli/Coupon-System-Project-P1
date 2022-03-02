package DB.Util;


import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ObjectExtractionUtil {

    public static Company resultSetToCompany(final ResultSet result) throws SQLException {
        return new Company(
                result.getInt("id"),
                result.getString("name"),
                result.getString("email"),
                result.getString("password")
        );
    }

    public static Company resultSetToCompany(final ResultSet result, final List<Coupon> companyCoupons) throws SQLException {
        final Company company = resultSetToCompany(result);
        company.setCoupons(companyCoupons);
        return company;
    }

    public static Coupon resultSetToCoupon(final ResultSet result) throws SQLException {
        return new Coupon(
                result.getInt("id"),
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
    public static Customer resultSetToCustomer(final ResultSet result) throws SQLException {
        return new Customer(
                result.getInt("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("email"),
                result.getString("password")
        );
    }
    public static Customer resultSetToCustomer(final ResultSet result, final List<Coupon> customerCoupons) throws SQLException {
        final Customer customer = resultSetToCustomer(result);
        customer.setCoupons(customerCoupons);
        return customer;
    }
}
package DB.Util;


import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ObjectExtractionUtil {

    public static Company resultSetToCompany(ResultSet result) throws SQLException {
        return new Company(
                result.getInt("id"),
                result.getString("name"),
                result.getString("email")
        );
    }

    public static Company resultSetToCompany(ResultSet result, List<Coupon> companyCoupons) throws SQLException {
        Company company = resultSetToCompany(result);
        company.setCoupons(companyCoupons);
        return company;
    }

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

    public static Customer resultSetToCustomer(ResultSet result) throws SQLException {
        return new Customer(
                result.getInt("customer_id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("email")
        );
    }

    public static Customer resultSetToCustomer(ResultSet result, List<Coupon> customerCoupons) throws SQLException {
        Customer customer = resultSetToCustomer(result);
        customer.setCoupons(customerCoupons);// todo consider deleting
        return customer;
    }
}
package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompanyFacadeDAO {
    void addCoupon(Coupon coupon) throws EntityAlreadyExistException, EntityCrudException;
    void updateCoupon(Coupon coupon) throws EntityCrudException;
    void deleteCoupon(int couponId);
    ArrayList<Coupon> getCompanyCoupons() throws EntityCrudException;
    ArrayList<Coupon> getCompanyCoupons(Category category) throws SQLException, EntityCrudException;
    ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws SQLException, EntityCrudException;
    Company getCompanyDetails();
}

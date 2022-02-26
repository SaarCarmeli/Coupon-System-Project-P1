package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Exceptions.EntityCrudException;

import java.util.ArrayList;

public interface CompanyFacadeDAO {
    void addCoupon(Coupon coupon) throws Exception;
    void updateCoupon(Coupon coupon) throws EntityCrudException;
    void deleteCoupon(int couponId);
    ArrayList<Coupon> getCompanyCoupons() throws EntityCrudException;
    ArrayList<Coupon> getCompanyCoupons(Category category);
    ArrayList<Coupon> getCompanyCoupons(double maxPrice);
    Company getCompanyDetails();
}

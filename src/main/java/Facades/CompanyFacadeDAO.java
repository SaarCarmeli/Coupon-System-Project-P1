package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;

import java.util.ArrayList;

public interface CompanyFacadeDAO {
    void addCoupon(Coupon coupon);
    void updateCoupon(Coupon coupon);
    void deleteCoupon(int couponId);
    ArrayList<Coupon> getCompanyCoupons();
    ArrayList<Coupon> getCompanyCoupons(Category category);
    ArrayList<Coupon> getCompanyCoupons(double maxPrice);
    Company getCompanyDetails();
}

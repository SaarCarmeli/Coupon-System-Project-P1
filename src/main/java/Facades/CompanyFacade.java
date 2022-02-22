package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;

import java.util.ArrayList;

public class CompanyFacade implements CompanyFacadeDAO {
    private final int companyId;

    public CompanyFacade(int companyId) {
        this.companyId = companyId;
    }

    // todo implement methods:
    @Override
    public void addCoupon(Coupon coupon) {

    }

    @Override
    public void updateCoupon(Coupon coupon) {

    }

    @Override
    public void deleteCoupon(int couponId) {

    }

    @Override
    public ArrayList<Coupon> getCompanyCoupons() {
        return null;
    }

    @Override
    public ArrayList<Coupon> getCompanyCoupons(Category category) {
        return null;
    }

    @Override
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
        return null;
    }

    @Override
    public Company getCompanyDetails() {
        return null;
    }
}

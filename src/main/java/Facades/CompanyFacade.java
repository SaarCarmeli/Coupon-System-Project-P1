package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DBDAO.CompanyDBDAO;
import DBDAO.CouponDBDAO;
import Exceptions.EntityCrudException;

import java.util.ArrayList;

public class CompanyFacade implements CompanyFacadeDAO {
    private final int companyId;
    private final Company company; // todo: is necessary?

    public CompanyFacade(int companyId) throws EntityCrudException {
        this.companyId = companyId;
        this.company = CompanyDBDAO.getInstance().readCompany(companyId); // todo: is necessary?
    }

    @Override
    public void addCoupon(Coupon coupon) throws Exception {
        if (CouponDBDAO.getInstance().isCouponExistByCompanyId(this.companyId, coupon.getTitle())) {
            throw new Exception(); //todo custom exception
        }
        CouponDBDAO.getInstance().createCoupon(coupon);
    }

    @Override
    public void updateCoupon(Coupon coupon) throws EntityCrudException {
        CouponDBDAO.getInstance().updateCoupon(coupon);
    }

    @Override
    public void deleteCoupon(int couponId) {

    }

    @Override
    public ArrayList<Coupon> getCompanyCoupons() throws EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().readCouponsByCompanyId(this.companyId);
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
        return this.company;
    }
}

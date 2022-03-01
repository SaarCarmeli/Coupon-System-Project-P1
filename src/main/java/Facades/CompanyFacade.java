package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DBDAO.CompanyDBDAO;
import DBDAO.CouponDBDAO;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade implements CompanyFacadeDAO {
    private final int companyId;
    private final Company company; // todo: is necessary?

    public CompanyFacade(int companyId) throws EntityCrudException {
        this.companyId = companyId;
        this.company = CompanyDBDAO.getInstance().readCompany(companyId); // todo: is necessary?
    }

    @Override
    public void addCoupon(Coupon coupon) throws EntityAlreadyExistException, EntityCrudException {
        if (CouponDBDAO.getInstance().isCouponExistByCompanyId(this.companyId, coupon.getTitle())) {
            throw new EntityAlreadyExistException(EntityType.COUPON);
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
    public ArrayList<Coupon> getCompanyCoupons(Category category) throws SQLException, EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().
                readCouponsByCompanyIdAndCategory(this.companyId, String.valueOf(category));
    }

    @Override
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws SQLException, EntityCrudException {
        return (ArrayList<Coupon>) CouponDBDAO.getInstance().
                readCouponsByCompanyIdAndMaxPrice(this.companyId, String.valueOf(maxPrice)); //todo:check why Price is String in SQL
    }

    @Override
    public Company getCompanyDetails() {
        return this.company;
    }
}

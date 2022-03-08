package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DB.Util.DBManager;
import DB.Util.DBTools;
import DB.Util.ObjectExtractionUtil;
import DBDAO.CompanyDBDAO;
import DBDAO.CouponDBDAO;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade implements CompanyFacadeDAO {
    private final int companyId;

    public CompanyFacade(int companyId) throws EntityCrudException {
        this.companyId = companyId;
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
        return null;
    }

    @Override
    public Company getCompanyDetails() throws EntityCrudException {
        CompanyDBDAO companyDBDAO = CompanyDBDAO.getInstance();
        return companyDBDAO.readCompany(companyId);
    }
}

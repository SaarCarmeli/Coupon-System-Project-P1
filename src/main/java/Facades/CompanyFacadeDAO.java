package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;

import java.util.ArrayList;

public interface CompanyFacadeDAO {
    void addCoupon(Coupon coupon) throws EntityAlreadyExistException, EntityCrudException;
    void updateCoupon(Coupon coupon) throws EntityCrudException;
    void deleteCoupon(int couponId) throws EntityCrudException;
    Coupon readCouponById(int couponId) throws EntityCrudException;
    ArrayList<Coupon> readAllCompanyCoupons() throws EntityCrudException;
    ArrayList<Coupon> readCompanyCoupons(Category category) throws EntityCrudException;
    ArrayList<Coupon> readCompanyCoupons(double maxPrice) throws EntityCrudException;
    Company getCompanyDetails() throws EntityCrudException;
}

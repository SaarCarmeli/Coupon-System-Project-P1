package DBDAO;

import Beans.Coupon;
import Exceptions.EntityCrudException;

import java.sql.SQLException;
import java.util.List;

public interface CouponDAO {
    void createCoupon(Coupon coupon) throws EntityCrudException;
    void addCouponPurchase(Integer customerId, Integer couponId) throws EntityCrudException;
    Coupon readCoupon(Integer couponId) throws EntityCrudException;
    List<Coupon> readAllCoupons() throws EntityCrudException;
    List<Coupon> readCouponsByCustomerId(Integer customerId) throws EntityCrudException;
    List<Coupon> readCouponsByCustomerIdAndMaxPrice(Integer customerId, Double price) throws EntityCrudException, SQLException;
    List<Coupon> readCouponsByCustomerIdAndCategory(Integer customerId, String category) throws EntityCrudException, SQLException;
    List<Coupon> readCouponsByCompanyId(Integer companyId) throws EntityCrudException;
    List<Coupon> readCouponsByCompanyIdAndMaxPrice(Integer companyId, String price) throws EntityCrudException, SQLException;
    List<Coupon> readCouponsByCompanyIdAndCategory(Integer companyId, String category) throws EntityCrudException, SQLException;
    void updateCoupon(Coupon coupon) throws EntityCrudException;
    void deleteCoupon(Integer couponID) throws EntityCrudException;
    void deleteExpiredCoupons(String date) throws EntityCrudException;
    boolean isCouponExistByCompanyId(Integer companyId, String title) throws EntityCrudException;
}

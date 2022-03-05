package DBDAO;

import Beans.Coupon;
import Exceptions.EntityCrudException;

import java.sql.SQLException;
import java.util.List;

public interface CouponDAO {
    Integer createCoupon(Coupon coupon) throws EntityCrudException;
    Coupon readCoupon(Integer couponId) throws EntityCrudException;
    List<Coupon> readCouponsByCustomerIdAndCategory(Integer customerId, String category) throws EntityCrudException, SQLException;
    List<Coupon> readCouponsByCustomerIdAndMaxPrice(Integer customerId, String price) throws EntityCrudException, SQLException;
    List<Coupon> readAllCoupons() throws EntityCrudException;
    List<Coupon> readCouponsByCompanyId(Integer companyId) throws EntityCrudException;
    List<Coupon> readCouponsByCompanyIdAndCategory(Integer companyId, String category) throws EntityCrudException, SQLException;
    List<Coupon> readCouponsByCompanyIdAndMaxPrice(Integer companyId, String price) throws EntityCrudException, SQLException;
    void updateCoupon(Coupon coupon) throws EntityCrudException;
    void deleteCoupon(Integer couponID) throws EntityCrudException;
    void deleteExpiredCoupons(String date) throws EntityCrudException;
    boolean isCouponExistByCompanyId(Integer companyId, String title) throws EntityCrudException;
    void addCouponPurchase(Integer couponId, Integer customerId) throws EntityCrudException;
    void deleteCouponPurchase(Integer couponId, Integer customerId) throws EntityCrudException;
    void deleteCouponHistory(Integer couponID) throws EntityCrudException;
    void deleteCouponsByCompanyId(Integer companyId) throws EntityCrudException;
}

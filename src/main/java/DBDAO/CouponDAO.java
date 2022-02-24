package DBDAO;

import Beans.Coupon;
import Exceptions.EntityCrudException;

import java.sql.SQLException;
import java.util.List;

public interface CouponDAO {
    Integer createCoupon(Coupon coupon) throws EntityCrudException;
    Coupon readCoupon(Integer couponId) throws EntityCrudException;
    List<Coupon> readAllCoupons() throws EntityCrudException;
    void updateCoupon(Coupon coupon) throws EntityCrudException;
    void deleteCoupon(Integer couponID) throws EntityCrudException;
    List<Coupon> readCouponsByCompanyId(final Integer companyId) throws EntityCrudException;
    void addCouponPurchase(Integer couponId, Integer customerId) throws EntityCrudException;
    void deleteCouponPurchase(Integer couponId, Integer customerId) throws EntityCrudException;
    public void deleteCouponHistory(Integer couponID) throws EntityCrudException;
}

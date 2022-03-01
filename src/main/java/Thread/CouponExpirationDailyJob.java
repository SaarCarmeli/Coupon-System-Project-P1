package Thread;

import DB.Util.DateUtil;
import DBDAO.CouponDBDAO;
import Exceptions.EntityCrudException;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class CouponExpirationDailyJob {
    private CouponDBDAO couponDBDAO;
    private boolean isRunning;
    private long dayInMilliseconds = 86_400 * 1000;


    //c'tor
    public CouponExpirationDailyJob() {
        Timer timer = new Timer();
        couponDBDAO = CouponDBDAO.getInstance();
        this.isRunning = true;
        TimerTask couponCleanUp = new TimerTask() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        couponDBDAO.deleteExpiredCoupons(DateUtil.getCurrentDate());
                        System.out.println("All expired coupons have been deleted !");
                    } catch (EntityCrudException e) {
                        //todo:customize Exception
                        e.printStackTrace();
                    }
                }
            }
        };
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 2);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        timer.schedule(couponCleanUp, today.getTime(), dayInMilliseconds);
    }

//        public boolean isRunning(){
//        return !isRunning;

    public void stopTask() {
        isRunning = false;
    }
}

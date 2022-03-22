package Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * Converts Date to String for TimerTask in CouponExpirationDailyJob
     *
     * @return Current Date in String
     */
    public static String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(new Date());
    }
}
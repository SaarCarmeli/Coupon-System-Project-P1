package DB.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(new Date());
    }
}
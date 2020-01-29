package project.neoroutes.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {

    public static long getCurrentTimeAsTimeStamp() {
        return getCurrentTimeAsDate().getTime();
    }

    public static Date getCurrentTimeAsDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTime();
    }


    public static long getTimeFromDate(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        return calendar.getTime().getTime();
    }


    public static Date getDateFromTimeStamp(long timeStamp) {
        return new Date(timeStamp);
    }
}

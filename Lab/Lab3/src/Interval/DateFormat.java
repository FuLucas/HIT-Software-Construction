package Interval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    public static String DateLongToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ret = sdf.format(time);
        return ret;
    }

    public static long StringToDateLong(String str) {
        long ret = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(str);
            ret = date.getTime();
        } catch (ParseException e) {
            System.out.println("Date Format Wrong: " + str);
        }
        return ret;
    }

    public static String HourDateLongToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        String ret = sdf.format(time);
        return ret;
    }

    public static long StringToHourDateLong(String str) {
        long ret = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        try {
            Date date = sdf.parse(str);
            ret = date.getTime();
        } catch (ParseException e) {
            System.out.println("Date Format Wrong: " + str);
        }
        return ret;
    }
}

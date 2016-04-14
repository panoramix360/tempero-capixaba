package com.creativityloop.android.temperocapixaba.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Guilherme on 11/11/2015.
 */
public class DateUtils {

    public static GregorianCalendar getToday() {
        GregorianCalendar data = new GregorianCalendar(TimeZone.getTimeZone("America/Brasilia"));
        data.set(GregorianCalendar.HOUR_OF_DAY, 0);
        data.set(GregorianCalendar.MINUTE, 0);
        data.set(GregorianCalendar.SECOND, 0);
        data.set(GregorianCalendar.MILLISECOND, 0);
        return data;
    }

    public static int getDayOfWeek(GregorianCalendar data) {
        return data.get(Calendar.DAY_OF_WEEK);
    }

    public static String formatDate(GregorianCalendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }
}

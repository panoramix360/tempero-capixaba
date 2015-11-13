package com.creativityloop.android.temperocapixaba.util;

import java.util.GregorianCalendar;

/**
 * Created by Guilherme on 11/11/2015.
 */
public class DateUtils {

    public static GregorianCalendar getToday() {
        GregorianCalendar data = new GregorianCalendar();
        data.set(GregorianCalendar.HOUR_OF_DAY, 0);
        data.set(GregorianCalendar.MINUTE, 0);
        data.set(GregorianCalendar.SECOND, 0);
        data.set(GregorianCalendar.MILLISECOND, 0);
        return data;
    }
}

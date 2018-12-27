package com.example.diffa.chattime.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class DateUtil {
    private static DateFormat fullDateFormat;

    static {
        fullDateFormat = new SimpleDateFormat("hh:mm a");
    }

    public static String toTimeDate(Date date) {
        return fullDateFormat.format(date);
    }
}

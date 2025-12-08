package com.IngSoftware.proyectosgr.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static String datePattern = "yyyy-MM-dd";
    private static String datePatternFull = "yyyy-MM-dd HH:mm:ss";

    public static Date convertStringToDate(String dateString, String dp) throws Exception{
        return new SimpleDateFormat(dp).parse(dateString);
    }
    public static String convertDateToString(Date date, String dp) throws Exception{
        return new SimpleDateFormat(dp).format(date);
    }

    public static Date getCurrentDateAndHour(){
        ZoneId zona = ZoneId.systemDefault();
        Date currentDate = Date.from(LocalDateTime.now().atZone(zona).toInstant());
        return currentDate;
    }

    public static Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
    public static Date addMinsToDate(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }
    public static Boolean differenceBetweenDates(Date date, int minutes) throws Exception {
        Long actualMsDate = getCurrentDateAndHour().getTime();
        Long antiqueMsDate = date.getTime();
        Long difference = (actualMsDate - antiqueMsDate) / (1000 * 60);
        return difference <= minutes;
    }
}
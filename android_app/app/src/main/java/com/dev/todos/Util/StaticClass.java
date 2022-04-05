package com.dev.todos.Util;

import java.util.Calendar;

public class StaticClass {

   public static Boolean fromChat = false;
    public static Boolean fromUser = false;

    public static Boolean fromTraveller = false;

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(yourdate);
        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);


        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);


        String shours = String.valueOf(hours);

        String sminutes = String.valueOf(minutes);

        String sseconds = String.valueOf(seconds);


        String sday = String.valueOf(day);
        if (sday.length() == 1) {
            sday = "0" + sday;
        }
        String smonth = String.valueOf(month);
        //smonth = smonth + 1;

        if (smonth.length() == 1) {
            smonth = "0" + smonth;
        }
        String syear = String.valueOf(year);
        String sdate = syear + "-" + smonth + "-" + sday;
        //String sdate = sday + "/" + smonth + "/" + syear;
        if (shours.length() == 1) {
            shours = "0" + shours;
        }
        if (sminutes.length() == 1) {
            sminutes = "0" + sminutes;
        }

        if (sseconds.length() == 1) {
            sseconds = "0" + sseconds;
        }
        String stime = shours + ":" + sminutes + ":" + sseconds;


        return sdate ;
    }

    public static String getTime() {
        Calendar calendar = Calendar.getInstance();

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        //int seconds = calendar.get(Calendar.SECOND);

        String sminutes = String.valueOf(minutes);
        String shours = String.valueOf(hours);
        if (shours.length() == 1) {
            shours = "0" + shours;
        }
        if (sminutes.length() == 1) {
            sminutes = "0" + sminutes;
        }
        String stime = shours + ":" + sminutes;

        return stime;
    }





}

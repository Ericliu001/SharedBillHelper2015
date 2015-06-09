package com.ericliudeveloper.sharedbillhelper.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eric.liu on 9/06/15.
 */
public final class MyDateUtils {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
            Locale.US);;

    private MyDateUtils() {
        // to prevent this class to be instantiated
    }


    public static String getStringFromDate(Date date) {
        return dateFormat.format(date);
    }

    public static int compareDates(String memberStartString,
                                   String memberEndtring, String billStartString, String billEndString) {

        int interval = 0;

        if (TextUtils.isEmpty(memberStartString) || TextUtils.isEmpty(memberEndtring) || TextUtils.isEmpty(billStartString) || TextUtils.isEmpty(billEndString)
                ) {
            return -1;
        }

        try {
            java.util.Date memberStartDate = dateFormat
                    .parse(memberStartString);
            java.util.Date memberEndDate = dateFormat.parse(memberEndtring);
            java.util.Date billStartDate = dateFormat.parse(billStartString);
            java.util.Date billEndDate = dateFormat.parse(billEndString);

            java.util.Date startDate = memberStartDate.after(billStartDate) ? memberStartDate
                    : billStartDate;
            java.util.Date endDate = memberEndDate.before(billEndDate) ? memberEndDate
                    : billEndDate;

            if (startDate.after(endDate)) {
                return -1;
            }

            interval = compareDates(startDate, endDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return interval;

    }


    public static String[] getPayeeStartEndDates(String memberStartString,
                                                 String memberEndtring, String billStartString, String billEndString){
        String[] startEndDates = new String[2];
        try {
            java.util.Date memberStartDate = dateFormat
                    .parse(memberStartString);
            java.util.Date memberEndDate = dateFormat.parse(memberEndtring);
            java.util.Date billStartDate = dateFormat.parse(billStartString);
            java.util.Date billEndDate = dateFormat.parse(billEndString);

            java.util.Date startDate = memberStartDate.after(billStartDate) ? memberStartDate
                    : billStartDate;
            java.util.Date endDate = memberEndDate.before(billEndDate) ? memberEndDate
                    : billEndDate;

            if (startDate.after(endDate)) {
            }


            startEndDates[0] = dateFormat.format(startDate);
            startEndDates[1] = dateFormat.format(endDate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return startEndDates;
    };

    public static int compareDates(String startDateString, String endDateString) {
        int interval = 0;

        if (TextUtils.isEmpty(startDateString) || TextUtils.isEmpty(endDateString)
                ) {
            return -1;
        }
        try {
            java.util.Date startDate = dateFormat.parse(startDateString);
            java.util.Date endDate = dateFormat.parse(endDateString);

            if (startDate.after(endDate)) {
                return -1;
            }

            interval = compareDates(startDate, endDate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return interval;
    }

    private static Calendar calendarStartDate = Calendar.getInstance();
    private static Calendar calendarEndDate = Calendar.getInstance();


    public static int compareDates(java.util.Date startDate,
                                   java.util.Date endDate) {
        int interval = 0;

        calendarStartDate = Calendar.getInstance();
        calendarStartDate.set(Calendar.YEAR, startDate.getYear());
        calendarStartDate.set(Calendar.MONTH, startDate.getMonth());
        calendarStartDate.set(Calendar.DAY_OF_MONTH, startDate.getDate());


        calendarEndDate.set(Calendar.YEAR, endDate.getYear());
        calendarEndDate.set(Calendar.MONTH, endDate.getMonth());
        calendarEndDate.set(Calendar.DAY_OF_MONTH, endDate.getDate());

        long diff = calendarEndDate.getTimeInMillis()
                - calendarStartDate.getTimeInMillis();
        interval = (int) (diff / (24 * 60 * 60 * 1000) + 1); // plus one day

        return interval;
    }
}

package com.ericliudeveloper.sharedbillhelper.util;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.dialog.DateWrongDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eric.liu on 9/06/15.
 */
public final class MyDateUtils {

    private static final String DATE_WRONG = "date wrong";
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
            Locale.US);
    ;

    private MyDateUtils() {
        // to prevent this class to be instantiated
    }


    public static String getCurrentTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        return dateFormat.format(new Date());
    }


    public static void checkStartDateAfterEndDateAndShowDialog(String start, String end, Activity activity) {
        if (activity == null) {
            return;
        }

        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            if (MyDateUtils.compareDates(start, end) < 0) {
                Bundle args = new Bundle();
                args.putString(DateWrongDialog.TITLE, activity.getResources().getString(R.string.date_picking_mistake));
                args.putString(DateWrongDialog.MESSAGE, activity.getResources().getString(R.string.start_date_must_be_before_end_date));
                DateWrongDialog dateWrongdialog = DateWrongDialog.newInstance(args);
                dateWrongdialog.show(activity.getFragmentManager(), DATE_WRONG);
            }
        }
    }


    public static String getStringFromDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Calculate how many days this member should pay for this bill
     * @param memberStartString
     * @param memberEndtring
     * @param billStartString
     * @param billEndString
     * @return
     */
    public static int calculateMemberPayingDays(String memberStartString,
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
                                                 String memberEndtring,
                                                 String billStartString,
                                                 String billEndString) {
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
                return null;
            }


            startEndDates[0] = dateFormat.format(startDate);
            startEndDates[1] = dateFormat.format(endDate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return startEndDates;
    }

    ;

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

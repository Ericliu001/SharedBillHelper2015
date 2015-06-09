package com.ericliudeveloper.sharedbillhelper.ui.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int monthOfYear = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog picker = new DatePickerDialog(getActivity(), this, year, monthOfYear,
                dayOfMonth);

        return picker;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        Bundle args = getArguments();
        Date date = new Date(year - 1900, monthOfYear, dayOfMonth);

        EventBus.getDefault().post(new CustomEvents.EventDatePicked(date));
    }


}

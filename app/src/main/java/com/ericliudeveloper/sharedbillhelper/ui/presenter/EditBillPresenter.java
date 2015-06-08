package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.ericliudeveloper.sharedbillhelper.ui.dialog.DatePickerFragment;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 8/06/15.
 */
public class EditBillPresenter {
    Activity mActivity;


    public EditBillPresenter(Activity activity) {
        mActivity = activity;
    }

    public void registerEventbusListener(){
        EventBus.getDefault().register(this);
    }

    public void onEvent(CustomEvents.DatePickedEvent event){
        Toast.makeText(mActivity, event.date.toString(), Toast.LENGTH_SHORT).show();
    }

    public void unregisterEventbusListener() {
        EventBus.getDefault().unregister(this);
    }

    public void startDateButtonClicked() {
        showDatePickDialog();
    }




    public void endDateButtonClicked() {
        showDatePickDialog();
    }

    public void dueDateButtonClicked() {
        showDatePickDialog();
    }

    private void showDatePickDialog() {
        new DatePickerFragment().show(mActivity.getFragmentManager(), "date_picker");
    }


    public void startActionDone() {
    }

    public void startActionCancel() {

    }
}

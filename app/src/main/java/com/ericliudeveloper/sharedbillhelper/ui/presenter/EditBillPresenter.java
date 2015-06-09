package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.app.Activity;

import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.ui.dialog.DatePickerFragment;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.util.MyDateUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 8/06/15.
 */
public class EditBillPresenter {
    Activity mActivity;
    Bill mBill;
    EditBillFace mCallback;

    private static final int ACTION_SELECT_START_DATE = 1;
    private static final int ACTION_SELECT_END_DATE = 2;
    private static final int ACTION_SELECT_DUE_DATE = 3;
    private int selectActionType = -1;


    public EditBillPresenter(Activity activity, EditBillFace callback) {
        mActivity = activity;
        mCallback = callback;
        mBill = new Bill();
    }

    public void registerEventbusListener(){
        EventBus.getDefault().register(this);
    }


    public void onEvent(CustomEvents.EventSendBill eventSendBill) {
        if (eventSendBill.bill == null) {
            return;
        }

        mBill = eventSendBill.bill;
    }

    public void onEvent(CustomEvents.EventDatePicked event) {
        if (event.date == null) {
            return;
        }

        String pickedDate = MyDateUtils.getStringFromDate(event.date);

        switch (selectActionType) {
            case ACTION_SELECT_START_DATE:
                //todo: handle start date after end date input
                mBill.setStartDate(pickedDate);
                mCallback.showPickedStartDate(pickedDate);
                break;


            case ACTION_SELECT_END_DATE:

                mBill.setEndDate(pickedDate);
                mCallback.showPickedEndDate(pickedDate);
                break;

            case ACTION_SELECT_DUE_DATE:

                mBill.setDueDate(pickedDate);
                mCallback.showPickedDueDate(pickedDate);
                break;

            default:
                break;
        }
    }

    public void unregisterEventbusListener() {
        EventBus.getDefault().unregister(this);
    }

    public void startDateButtonClicked() {
        selectActionType = ACTION_SELECT_START_DATE;
        showDatePickDialog();
    }

    public void endDateButtonClicked() {
        selectActionType = ACTION_SELECT_END_DATE;
        showDatePickDialog();
    }

    public void dueDateButtonClicked() {
        selectActionType = ACTION_SELECT_DUE_DATE;
        showDatePickDialog();
    }

    private void showDatePickDialog() {
        new DatePickerFragment().show(mActivity.getFragmentManager(), "date_picker");
    }


    public void startActionDone() {
    }

    public void startActionCancel() {

    }


    public interface EditBillFace {
        void showPickedStartDate(String pickedDate);
        void showPickedEndDate(String pickedDate);
        void showPickedDueDate(String pickedDate);
    }
}

package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.BillDAO;
import com.ericliudeveloper.sharedbillhelper.ui.dialog.DatePickerFragment;
import com.ericliudeveloper.sharedbillhelper.ui.dialog.DateWrongDialog;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.util.MyDateUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 8/06/15.
 */
public class EditBillPresenter {
    private static final String DATE_WRONG = "date_wrong";
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

    public void registerEventbusListener() {
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
        checkIfStartDateAfterEndDate();
    }

    public void onEvent(CustomEvents.EventWrongDatePicked eventWrongDatePicked) {
        showDatePickDialog();
    }

    public void unregisterEventbusListener() {
        EventBus.getDefault().unregister(this);
    }


    private void checkIfStartDateAfterEndDate() {
        String start = mBill.getStartDate();
        String end = mBill.getEndDate();
        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            if (MyDateUtils.compareDates(start, end) < 0) {
                Bundle args = new Bundle();
                args.putString(DateWrongDialog.TITLE, mActivity.getResources().getString(R.string.date_picking_mistake));
                args.putString(DateWrongDialog.MESSAGE, mActivity.getResources().getString(R.string.start_date_must_be_before_end_date));
                DateWrongDialog dateWrongdialog = DateWrongDialog.newInstance(args);
                dateWrongdialog.show(mActivity.getFragmentManager(), DATE_WRONG);
            }
        }
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
        String type = mCallback.getBillTypeInput();
        double amount = Double.valueOf(mCallback.getAmountInput());
        int isPaid = mCallback.getIsPaidInput() ? 1 : 0;

        mBill.setType(type);
        mBill.setAmount(amount);
        mBill.setPaid(isPaid);

        saveBillInstanceToDB(mBill);
    }

    private void saveBillInstanceToDB(Bill bill) {
        new BillDAO().saveBill(bill, null);
    }

    public void startActionCancel() {
        mActivity.finish();
    }


    public interface EditBillFace {
        void showPickedStartDate(String pickedDate);

        void showPickedEndDate(String pickedDate);

        void showPickedDueDate(String pickedDate);

        String getBillTypeInput();

        String getAmountInput();

        boolean getIsPaidInput();
    }
}

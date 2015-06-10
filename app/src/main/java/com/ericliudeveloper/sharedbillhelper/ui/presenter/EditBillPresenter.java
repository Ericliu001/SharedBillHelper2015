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


    public EditBillPresenter(EditBillFace callback) {
        mCallback = callback;
        mBill = new Bill();
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }


    public void registerEventbusListener() {
        EventBus.getDefault().registerSticky(this);
    }


    public void onEvent(CustomEvents.EventEditBill eventEditBill) {
        if (eventEditBill.bill == null) {
            return;
        }

        mBill = eventEditBill.bill;
        refreshDisplayFromBillInstance(mBill);
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

        mActivity.finish();
    }

    private void saveBillInstanceToDB(Bill bill) {
        BillDAO.saveBill(bill, null);
    }

    public void startActionCancel() {
        mActivity.finish();
    }

    public void refreshDisplayFromBillInstance(Bill bill) {
        String type = bill.getType();
        String amount = String.valueOf( bill.getAmount());
        String start = bill.getStartDate();
        String end = bill.getEndDate();
        String due = bill.getDueDate();
        boolean isPaid = bill.getPaid() > 0;

        mCallback.showBillType(type);
        mCallback.showAmount(amount);
        mCallback.showPickedStartDate(start);
        mCallback.showPickedEndDate(end);
        mCallback.showPickedDueDate(due);
        mCallback.showIsPaid(isPaid);
    }


    public interface EditBillFace {

        void showBillType(String type);

        void showAmount(String amount);

        void showPickedStartDate(String pickedDate);

        void showPickedEndDate(String pickedDate);

        void showPickedDueDate(String pickedDate);

        void showIsPaid(boolean isPaid);

        String getBillTypeInput();

        String getAmountInput();

        boolean getIsPaidInput();
    }
}

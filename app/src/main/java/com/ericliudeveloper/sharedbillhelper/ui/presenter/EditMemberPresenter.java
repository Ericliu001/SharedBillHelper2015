package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.MemberDAO;
import com.ericliudeveloper.sharedbillhelper.ui.dialog.DatePickerFragment;
import com.ericliudeveloper.sharedbillhelper.ui.dialog.DateWrongDialog;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.util.MyDateUtils;
import com.ericliudeveloper.sharedbillhelper.util.PermissionsUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 11/06/15.
 */
public class EditMemberPresenter extends BasePresenter {


    private static final int ACTION_SELECT_MOVE_IN_DATE = 10;
    private static final int ACTION_SELECT_MOVE_OUT_DATE = 11;
    private static final String DATE_WRONG = "wrong date";
    private static final int REQUEST_PICK_CONTACT = 1;
    private Activity mActivity;
    private Member mMember;
    private EditMemberFace mCallback;
    private int selectDateType = -1;


    public EditMemberPresenter(EditMemberFace callback) {
        mCallback = callback;
        mMember = new Member();
    }


    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public void refreshDisplayFromMemberInstance() {
        String firstName = mMember.getFirstName();
        if (!TextUtils.isEmpty(firstName)) {
            mCallback.showMemberFirstName(firstName);
        }

        String lastName = mMember.getLastName();
        if (!TextUtils.isEmpty(lastName)) {
            mCallback.showMemberLastName(lastName);
        }

        String phone = mMember.getPhone();
        if (!TextUtils.isEmpty(phone)) {
            mCallback.showMemberPhoneNumber(phone);
        }

        String email = mMember.getEmail();
        if (!TextUtils.isEmpty(email)) {
            mCallback.showMemberEmail(email);
        }

        String moveIn = mMember.getMoveInDate();
        if (!TextUtils.isEmpty(moveIn)) {
            mCallback.showPickedMoveInDate(moveIn);
        }

        String moveOut = mMember.getMoveOutDate();
        if (!TextUtils.isEmpty(moveOut)) {
            mCallback.showPickedMoveOutDate(moveOut);
        }

    }

    public void startActionDone() {
        String firstName = mCallback.getFirstNameInput();
        if (!TextUtils.isEmpty(firstName)) {
            mMember.setFirstName(firstName);
        } else {
            mCallback.showErroDialog();
            return;
        }

        String lastName = mCallback.getLastNameInput();
        if (!TextUtils.isEmpty(lastName)) {
            mMember.setLastName(lastName);
        }

        String phone = mCallback.getPhoneNumberInput();
        if (!TextUtils.isEmpty(phone)) {
            mMember.setPhone(phone);
        }

        String email = mCallback.getEmailInput();
        if (!TextUtils.isEmpty(email)) {
            mMember.setEmail(email);
        }

        saveMemberInstanceToDB(mMember);

        mActivity.finish();
    }

    public void startActionCancel() {
        mActivity.finish();
    }

    public void startActionPickContact() {
        PermissionsUtil.checkReadContactsPermission(mActivity);
    }


    private void saveMemberInstanceToDB(Member mMember) {
        MemberDAO.saveMember(mMember, null);
    }


    private void showDatePickDialog() {
        new DatePickerFragment().show(mActivity.getFragmentManager(), "date_picker");
    }

    public void onPickMoveInDateButtonClicked() {
        selectDateType = ACTION_SELECT_MOVE_IN_DATE;
        showDatePickDialog();
    }

    public void onPickMoveOutDateButtonClicked() {
        selectDateType = ACTION_SELECT_MOVE_OUT_DATE;
        showDatePickDialog();
    }


    public interface EditMemberFace {


        void showPickedMoveInDate(String pickedDate);

        void showPickedMoveOutDate(String pickedDate);

        void showMemberFirstName(String firstName);

        void showMemberLastName(String lastName);

        void showMemberPhoneNumber(String phone);

        void showMemberEmail(String email);

        String getFirstNameInput();

        String getLastNameInput();

        String getPhoneNumberInput();

        String getEmailInput();

        void showErroDialog();
    }


    public void onEvent(CustomEvents.ReadContactPermissionGrantedEvent eventReadContactPermissionGranted) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        mActivity.startActivityForResult(pickContactIntent, REQUEST_PICK_CONTACT);

        EventBus.getDefault().removeStickyEvent(eventReadContactPermissionGranted);
    }

    public void onEvent(CustomEvents.EventActionContactChosen eventActionContactChosen) {
        mCallback.showMemberFirstName(eventActionContactChosen.member.getFirstName());
        mCallback.showMemberPhoneNumber(eventActionContactChosen.member.getPhone());
        EventBus.getDefault().removeStickyEvent(eventActionContactChosen);
    }


    public void onEvent(CustomEvents.EventEditMember eventEditMember) {
        if (eventEditMember.member == null) {
            return;
        }

        mMember = eventEditMember.member;
        refreshDisplayFromMemberInstance();
        EventBus.getDefault().removeStickyEvent(eventEditMember);
    }


    public void onEvent(CustomEvents.EventDatePicked eventDatePicked) {
        if (eventDatePicked.date == null) {
            return;
        }

        String pickedDate = MyDateUtils.getStringFromDate(eventDatePicked.date);

        switch (selectDateType) {
            case ACTION_SELECT_MOVE_IN_DATE:
                mMember.setMoveInDate(pickedDate);
                mCallback.showPickedMoveInDate(pickedDate);
                break;

            case ACTION_SELECT_MOVE_OUT_DATE:
                mMember.setMoveOutDate(pickedDate);
                mCallback.showPickedMoveOutDate(pickedDate);
                break;


            default:
                break;
        }

        MyDateUtils.checkStartDateAfterEndDateAndShowDialog(mMember.getMoveInDate(), mMember.getMoveOutDate(), mActivity);
    }

    public void onEvent(CustomEvents.EventWrongDatePicked eventWrongDatePicked) {
        showDatePickDialog();
    }


    private void checkIfStartDateAfterEndDate() {
        String moveIn = mMember.getMoveInDate();
        String moveOut = mMember.getMoveOutDate();
        if (!TextUtils.isEmpty(moveIn) && !TextUtils.isEmpty(moveOut)) {
            if (MyDateUtils.compareDates(moveIn, moveOut) < 0) {
                Bundle args = new Bundle();
                args.putString(DateWrongDialog.TITLE, mActivity.getResources().getString(R.string.date_picking_mistake));
                args.putString(DateWrongDialog.MESSAGE, mActivity.getResources().getString(R.string.start_date_must_be_before_end_date));
                DateWrongDialog dateWrongdialog = DateWrongDialog.newInstance(args);
                dateWrongdialog.show(mActivity.getFragmentManager(), DATE_WRONG);
            }
        }
    }
}

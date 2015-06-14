package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.model.Member;
import com.ericliudeveloper.sharedbillhelper.model.MemberDAO;
import com.ericliudeveloper.sharedbillhelper.ui.activity.CalculationParameterActivity;
import com.ericliudeveloper.sharedbillhelper.ui.activity.EditMemberActivity;
import com.ericliudeveloper.sharedbillhelper.ui.dialog.DeleteDialog;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;
import com.ericliudeveloper.sharedbillhelper.util.Router;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMemberDetailsFragment extends BaseFragment {
    private Member mMember;


    @InjectView(R.id.tvFristName)
    TextView tvFirstName;

    @InjectView(R.id.tvLastName)
    TextView tvLastName;

    @InjectView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;

    @InjectView(R.id.tvEmail)
    TextView tvEmail;

    @InjectView(R.id.tvMoveInDate)
    TextView tvMoveInDate;

    @InjectView(R.id.tvMoveOutDate)
    TextView tvMoveOutDate;

    public ViewMemberDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_member_details, container, false);
        ButterKnife.inject(this, root);
        return root;
    }


    @Override
    public void onResume() {
        EventBus.getDefault().registerSticky(this);
        super.onResume();
    }

    public void onEvent(CustomEvents.EventViewMember eventViewMember) {
        mMember = eventViewMember.member;
        refreshDisplay(mMember);
    }

    private void refreshDisplay(Member mMember) {
        String firstName = mMember.getFirstName();
        if (!TextUtils.isEmpty(firstName)) {
            tvFirstName.setText(firstName);
        }

        String lastName = mMember.getLastName();
        if (!TextUtils.isEmpty(lastName)) {
            tvLastName.setText(lastName);
        }

        String phone = mMember.getPhone();
        if (!TextUtils.isEmpty(phone)) {
            tvPhoneNumber.setText(phone);
        }

        String email = mMember.getEmail();
        if (!TextUtils.isEmpty(email)) {
            tvEmail.setText(email);
        }

        String moveIn = mMember.getMoveInDate();
        if (!TextUtils.isEmpty(moveIn)) {
            tvMoveInDate.setText(moveIn);
        }

        String moveOut = mMember.getMoveOutDate();
        if (!TextUtils.isEmpty(moveOut)) {
            tvMoveOutDate.setText(moveOut);
        }
    }

    public void onEvent(CustomEvents.EventActionDelete eventActionDelete) {
        if (mMember == null) {
            return;
        }

        int yesDeleted = 1;
        mMember.setDeleted(yesDeleted);
        MemberDAO.saveMember(mMember, null);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void onEvent(CustomEvents.EventFABClicked eventFABClicked) {
        Router.startActivity(getActivity(), CalculationParameterActivity.class, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_view_details, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit:
                EventBus.getDefault().postSticky(new CustomEvents.EventEditMember(mMember));
                Intent gotoEditIntent = new Intent(getActivity(), EditMemberActivity.class);
                getActivity().startActivity(gotoEditIntent);
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;


            case R.id.action_delete:
                showDeleteMemberDialog();

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteMemberDialog() {
        Bundle args = new Bundle();
        args.putString(DeleteDialog.TITLE,
                getResources().getString(R.string.confirm_delete));
        args.putString(DeleteDialog.MESSAGE,
                getResources().getString(R.string.delete_member));
        DeleteDialog deleteDialog = DeleteDialog.newInstance(args);
        deleteDialog.show(getFragmentManager(), "delete");
    }

}

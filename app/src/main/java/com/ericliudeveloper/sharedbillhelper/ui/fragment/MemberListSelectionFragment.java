package com.ericliudeveloper.sharedbillhelper.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.activity.EditMemberActivity;
import com.ericliudeveloper.sharedbillhelper.util.ResouceUtils;
import com.ericliudeveloper.sharedbillhelper.util.Router;

/**
 * Created by liu on 15/06/15.
 */
public class MemberListSelectionFragment extends MemberListFragment {

    public MemberListSelectionFragment() {
        isListSelectionMode = true;
    }

    @Override
    protected void displayEmptyView(boolean isEmpty) {
        if (isEmpty) {
            Button createBillButton = new Button(getActivity());
            int pixels = ResouceUtils.getAppResources().getDimensionPixelSize(R.dimen.create_new_button_height);
            createBillButton.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            createBillButton.setText(ResouceUtils.getAppResources().getString(R.string.no_member_create));
            createBillButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Router.startActivity(getActivity(), EditMemberActivity.class, null);
                }
            });
            mEmptyView.addView(createBillButton);
            mEmptyView.setVisibility(View.VISIBLE);


        } else {
            mEmptyView.removeAllViews();
            mEmptyView.setVisibility(View.GONE);
        }
    }

}

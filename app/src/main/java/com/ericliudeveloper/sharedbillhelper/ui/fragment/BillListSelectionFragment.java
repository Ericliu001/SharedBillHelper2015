package com.ericliudeveloper.sharedbillhelper.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.ericliudeveloper.sharedbillhelper.R;

/**
 * Created by liu on 14/06/15.
 */
public class BillListSelectionFragment extends BillListFragment {
    public BillListSelectionFragment() {
        isListSelectionMode = true;
    }


    @Override
    protected void checkListEmpty() {
        if (mAdapter.getItemCount() == 0) {
            ImageView ivEmptyBillList = new ImageView(getActivity());
            ivEmptyBillList.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_assignment));
            mEmptyView.addView(ivEmptyBillList);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mEmptyView.removeAllViews();
        }
    }
}

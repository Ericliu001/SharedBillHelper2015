package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.ViewBillDetailsFragment;

public class ViewBillDetailsActivity extends ContainerActivity {


    @Override
    protected int getChildLayoutResID() {
        return R.layout.layout_detail;
    }

    @Override
    protected Fragment getFragment() {
        return new ViewBillDetailsFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }
}

package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;

import com.ericliudeveloper.sharedbillhelper.ui.fragment.PaymentListFragment;

/**
 * Created by liu on 20/06/15.
 */
public class PaymentListActivity extends  ContainerActivity {
    @Override
    protected Fragment getFragment() {
        return new PaymentListFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }
}

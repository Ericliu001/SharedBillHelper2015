package com.ericliudeveloper.sharedbillhelper.ui;

import android.app.Fragment;

import com.ericliudeveloper.sharedbillhelper.ui.fragment.EditBillFragment;

public class EditBillActivity extends ContainerActivity {


    @Override
    protected Fragment getFragment() {
        return new EditBillFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }
}

package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;

import com.ericliudeveloper.sharedbillhelper.ui.fragment.EditMemberFragment;

/**
 * Created by liu on 11/06/15.
 */
public class EditMemberActivity extends ContainerActivity {

    @Override
    protected Fragment getFragment() {
        return new EditMemberFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }

}

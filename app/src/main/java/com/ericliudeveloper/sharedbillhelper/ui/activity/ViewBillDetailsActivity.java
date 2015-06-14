package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;
import android.graphics.drawable.Drawable;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.ViewBillDetailsFragment;

public class ViewBillDetailsActivity extends DetailsViewActivity {


    @Override
    protected Fragment getFragment() {
        return new ViewBillDetailsFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }


    @Override
    protected Drawable getAppbarImageDrawable() {
        return  getResources().getDrawable(R.drawable.bills_appbar);
    }
}


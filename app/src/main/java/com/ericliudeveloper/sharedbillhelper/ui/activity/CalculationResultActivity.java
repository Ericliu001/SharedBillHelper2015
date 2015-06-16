package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;
import android.graphics.drawable.Drawable;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.CalculationResultsFragment;

public class CalculationResultActivity extends DetailsViewActivity {


    @Override
    protected Drawable getAppbarImageDrawable() {
        return getResources().getDrawable(R.drawable.split_bills);
    }

    @Override
    protected Fragment getFragment() {
        return new CalculationResultsFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }
}

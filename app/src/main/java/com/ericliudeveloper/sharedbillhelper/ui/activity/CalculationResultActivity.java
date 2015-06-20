package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.CalculationResultsFragment;
import com.ericliudeveloper.sharedbillhelper.util.Router;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_list_white_24dp));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.startActivity(CalculationResultActivity.this, PaymentListActivity.class, null);
            }
        });
    }
}

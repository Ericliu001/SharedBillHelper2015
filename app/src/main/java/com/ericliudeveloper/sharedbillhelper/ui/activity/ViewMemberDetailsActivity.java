package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;
import android.graphics.drawable.Drawable;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.ViewMemberDetailsFragment;

/**
 * Created by eric.liu on 11/06/15.
 */
public class ViewMemberDetailsActivity extends DetailsViewActivity {





    @Override
    protected Fragment getFragment() {
        return new ViewMemberDetailsFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }


    @Override
    protected Drawable getAppbarImageDrawable() {
        return getResources().getDrawable(R.drawable.housemates_appbar);
    }
}

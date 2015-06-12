package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.ViewMemberDetailsFragment;

/**
 * Created by eric.liu on 11/06/15.
 */
public class ViewMemberDetailsActivity extends ContainerActivity {




    @Override
    protected int getChildLayoutResID() {
        return R.layout.layout_detail;
    }

    @Override
    protected Fragment getFragment() {
        return new ViewMemberDetailsFragment();
    }

    @Override
    protected String getFragmentTag() {
        return getClass().getName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Drawable appbarImage = getResources().getDrawable(R.drawable.housemates_appbar);
        imageView.setImageDrawable(appbarImage);


    }

}

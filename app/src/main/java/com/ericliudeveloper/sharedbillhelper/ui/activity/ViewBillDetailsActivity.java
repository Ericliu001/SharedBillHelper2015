package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Drawable appbarImage = getResources().getDrawable(R.drawable.bills_appbar);
        imageView.setImageDrawable(appbarImage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




}


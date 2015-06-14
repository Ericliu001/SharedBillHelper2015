package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.util.CustomEvents;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 14/06/15.
 */
public abstract class DetailsViewActivity extends ContainerActivity {


    FloatingActionButton mFab;
    protected abstract Drawable getAppbarImageDrawable();


    @Override
    protected int getChildLayoutResID() {
        return R.layout.layout_detail;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupAppbarImage();

        setupFABClickListener();
    }


    private void setupFABClickListener() {
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CustomEvents.EventFABClicked());
            }
        });
    }

    private void setupAppbarImage() {

        Drawable appbarImage = getAppbarImageDrawable();
        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        imageView.setImageDrawable(appbarImage);
    }



}

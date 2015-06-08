package com.ericliudeveloper.sharedbillhelper.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ericliudeveloper.sharedbillhelper.R;

/**
 * Created by liu on 8/06/15.
 */
public abstract class ContainerActivity extends AppCompatActivity{

    protected abstract Fragment getFragment();
    protected abstract String getFragmentTag();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_container);

        String tag = getFragmentTag();
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag(tag) == null) {
            Fragment frag = getFragment();
            fm.beginTransaction().add(R.id.container, frag, tag).commit();
        }
    }
}

package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.ericliudeveloper.sharedbillhelper.R;

/**
 * Created by liu on 8/06/15.
 */
public abstract class ContainerActivity extends AppCompatActivity {

    protected abstract Fragment getFragment();

    protected abstract String getFragmentTag();

    /**
     * In case the subclass wants to use a different layout xml file,
     * the child class can override this method to supply the layout resource id
     *
     * @return
     */
    protected int getChildLayoutResID() {
        return -1;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResID = getChildLayoutResID();
        if (layoutResID > 0) {
            setContentView(layoutResID);
        } else {
            setContentView(R.layout.empty_container);
        }

        View container = findViewById(R.id.container);
        if (container != null) {

            String tag = getFragmentTag();
            FragmentManager fm = getFragmentManager();
            if (fm.findFragmentByTag(tag) == null) {
                Fragment frag = getFragment();
                fm.beginTransaction().add(R.id.container, frag, tag).commit();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

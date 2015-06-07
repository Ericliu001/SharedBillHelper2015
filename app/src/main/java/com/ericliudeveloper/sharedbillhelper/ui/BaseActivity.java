package com.ericliudeveloper.sharedbillhelper.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ericliudeveloper.sharedbillhelper.R;

/**
 * Created by liu on 6/06/15.
 */
public class BaseActivity extends AppCompatActivity {
    private android.support.v4.widget.DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setUpToolbar();
        }

        if (mDrawerLayout != null) {
            setUpNavigationDrawer();
        }

        if (mToolbar != null && mDrawerLayout != null) {
            setUpDrawerToggle();
        }

    }

    private void setUpDrawerToggle() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, mToolbar, R.string.app_name,R.string.app_name);

        actionBarDrawerToggle.syncState();
    }

    private void setUpToolbar() {
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setUpNavigationDrawer() {
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

    }
}

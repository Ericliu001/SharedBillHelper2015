package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ericliudeveloper.sharedbillhelper.R;

/**
 * Created by liu on 6/06/15.
 */
public class BaseActivity extends AppCompatActivity {
    protected android.support.v4.widget.DrawerLayout mDrawerLayout;
    protected Toolbar mToolbar;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    protected ViewPager mViewPager;
    protected TabLayout mTabLayout;
    protected NavigationView mNavigationView;


    protected static final int NAVDRAWER_ITEM_INVALID = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        if (mToolbar != null) {
            setupToolbar();
        }

        if (mDrawerLayout != null) {
            setupNavigationDrawer();
        }

        if (mNavigationView != null) {
            setupNavigationView();
        }

        if (mToolbar != null && mDrawerLayout != null) {
            setupDrawerToggle();
        }

        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        if (mTabLayout != null && mViewPager != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }

    }


    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * of BaseActivity override this to indicate what nav drawer item corresponds to them
     * Return NAVDRAWER_ITEM_INVALID to mean that this Activity should not have a Nav Drawer.
     */
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_INVALID;
    }

    private void setupNavigationView() {
        final int selfItem = getSelfNavDrawerItem();

        if (selfItem == NAVDRAWER_ITEM_INVALID) {
            // Navigation drawer not available
            return;
        }

        // select the correct nav menu item
        MenuItem currentItem = mNavigationView.getMenu().findItem(selfItem);
        currentItem.setEnabled(false);
        currentItem.setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        Intent mainIntent = new Intent(BaseActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.nav_calculator:
                        Intent calculatorIntent = new Intent(BaseActivity.this, CalculationParameterActivity.class);
                        calculatorIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(calculatorIntent);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.nav_reports:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    default:
                        break;
                }


                return false;
            }
        });
    }


    private void setupDrawerToggle() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);

        actionBarDrawerToggle.syncState();
    }

    private void setupToolbar() {
//        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setupNavigationDrawer() {
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    /**
     * For subclasses which have Viewpagers,
     * they should override this method to setup the Pager Adapter
     */
    protected void setupViewPager(ViewPager pager) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}

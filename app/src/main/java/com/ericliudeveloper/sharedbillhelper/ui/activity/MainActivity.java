package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.adapter.PagerAdapter;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.BillListFragment;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.MemberListFragment;
import com.ericliudeveloper.sharedbillhelper.util.Router;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    FloatingActionButton mFAB;
    View.OnClickListener createMemberClickListener, createBillClickListener;
    Router mRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);
        setupFloatActionButton();
        mRouter = new Router(MainActivity.this);
    }

    private void setupFloatActionButton() {
        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        createBillClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRouter.startActivity(EditBillActivity.class, null);
            }
        };

        createMemberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, EditMemberActivity.class);
//                startActivity(intent);
                //TODO: create member here
                Snackbar.make(v, "Create Member", Snackbar.LENGTH_SHORT).show();
            }
        };


        mFAB.setImageDrawable(getResources().getDrawable(R.drawable.ic_note_add_white_24dp));
        mFAB.setOnClickListener(createBillClickListener);
    }


    @Override
    protected void setupViewPager(ViewPager viewPager) {
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        pagerAdapter.addFragment(new BillListFragment(), "Bill");
        pagerAdapter.addFragment(new MemberListFragment(), "Member");
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {

            mFAB.setImageDrawable(getResources().getDrawable(R.drawable.ic_note_add_white_24dp));
            mFAB.setOnClickListener(createBillClickListener);
        }

        if (position == 1) {
            mFAB.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_add_white_24dp));
            mFAB.setOnClickListener(createMemberClickListener);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

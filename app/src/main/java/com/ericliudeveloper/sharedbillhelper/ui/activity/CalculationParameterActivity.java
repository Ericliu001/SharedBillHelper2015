package com.ericliudeveloper.sharedbillhelper.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.BillListSelectionFragment;
import com.ericliudeveloper.sharedbillhelper.ui.fragment.MemberListSelectionFragment;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.CalculationParameterPresenter;

/**
 * Created by liu on 14/06/15.
 */
public class CalculationParameterActivity extends BaseActivity {

    BillListSelectionFragment mBillFragment;
    MemberListSelectionFragment mMemberFragment;

    CalculationParameterPresenter mPresenter;

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_calculator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new CalculationParameterPresenter();

        setContentView(R.layout.activity_calculation_parameter);
//        mBillFragment = (BillListSelectionFragment) getFragmentManager().findFragmentById(R.id.billListFragment);
//        mMemberFragment = (MemberListSelectionFragment) getFragmentManager().findFragmentById(R.id.memberListFragment);
    }




    @Override
    protected void onPostResume() {
        super.onPostResume();
        mPresenter.registerEventBusListener();
    }



    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unregisterEventBusListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_calcuation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_calculate) {
            mPresenter.onActionCalculate();
        }

        return super.onOptionsItemSelected(item);
    }
}

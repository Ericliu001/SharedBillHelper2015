package com.ericliudeveloper.sharedbillhelper.presenter;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;

import com.ericliudeveloper.sharedbillhelper.ui.presenter.CalculationResultPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ericliu on 3/11/2015.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class CalculationResultPresenterTest implements CalculationResultPresenter.CalculationResultFace {
    private final String TAG = this.getClass().getSimpleName();
    private CalculationResultPresenter presenter;

    @Before
    public void setup() {
        presenter = new CalculationResultPresenter(this);
    }


    @Test
    public void sendSMS() {
        presenter.startActionSend();
    }


    @After
    public void tearDown() {
        presenter = null;

    }

    @Override
    public void showTotalAmount(String amount) {

    }

    @Override
    public void showNumberOfMembersPaying(String numMembers) {

    }

    @Override
    public void showNumberOfBillsPaid(String numBills) {

    }

    @Override
    public void startActivity(Intent intent) {
        String textStr = intent.getStringExtra(Intent.EXTRA_TEXT);
        assertThat("String is the same", textStr.startsWith("Total"));
        Log.d(TAG, textStr);
    }
}

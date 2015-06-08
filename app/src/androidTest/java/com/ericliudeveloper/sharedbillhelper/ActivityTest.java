package com.ericliudeveloper.sharedbillhelper;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.ericliudeveloper.sharedbillhelper.model.Bill;
import com.ericliudeveloper.sharedbillhelper.model.BillDAO;
import com.ericliudeveloper.sharedbillhelper.ui.TestActivity;

/**
 * Created by liu on 7/06/15.
 */
public class ActivityTest extends ActivityInstrumentationTestCase2<TestActivity> {
    Activity mActivity;
    ContentResolver mResolver;


    public ActivityTest() {
        super(TestActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mResolver = mActivity.getContentResolver();
    }

    public void testBillDao(){
        BillDAO billDAO = new BillDAO(mActivity);
        Bill bill = new Bill();
        bill.setType("tank cannon");
        bill.setAmount(2342.45);

        billDAO.saveBill(bill, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Uri uri = (Uri) msg.obj;
                Log.d("eric", "suprise " + uri.toString());
                assertNotNull("returned uri is null", uri);
            }
        });


//
//        Cursor cursor = mResolver.query(billUri, projectionBill, null, null, null);
//        String updatedType = "";
//        String updatedAmount = "";
//        if (cursor.moveToFirst()) {
//            updatedType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_TYPE));
//            updatedAmount = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BillColumns.COL_AMOUNT));
//        }
//
//        assertEquals("Type is wrong", "tank cannon", updatedType);
//        assertEquals("Amount is wrong", "2342.45", updatedAmount);
//
//        bill.setId(1);
//        bill.setAmount(100);
//        billDAO.saveBill(bill, null);




    }
}

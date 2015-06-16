package com.ericliudeveloper.sharedbillhelper.model;

import android.content.ContentResolver;

import com.ericliudeveloper.sharedbillhelper.MyApplication;

/**
 * Created by liu on 7/06/15.
 */
public interface Dao {
    int MESSAGE_WHAT_SAVED_BILL_URL = 40;
    int MESSAGE_WHAT_GET_BILL = 41;

    int MESSAGE_WHAT_SAVED_MEMBER_URL = 42;


    int MESSAGE_WHAT_SAVED_PAYMENT_URL = 43;

    int MESSAGE_WHAT_SAVED_PAYMENTINFO_URL = 44;


     ContentResolver mContentResolver = MyApplication.getAppContentResolver();
}

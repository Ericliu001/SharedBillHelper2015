package com.ericliudeveloper.sharedbillhelper;

import android.app.Application;
import android.content.ContentResolver;

/**
 * Created by liu on 6/06/15.
 */
public class MyApplication extends Application {

    private static Application mApplication;
    private static ContentResolver mContentResolver;

    public static Application getApplication() {
        return mApplication;
    }

    public static ContentResolver getAppContentResolver(){
        return mContentResolver;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mApplication == null){
            mApplication = this;
            mContentResolver = mApplication.getContentResolver();
        }
    }
}

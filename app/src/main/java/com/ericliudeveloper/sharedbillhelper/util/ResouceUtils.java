package com.ericliudeveloper.sharedbillhelper.util;

import android.content.res.Resources;

import com.ericliudeveloper.sharedbillhelper.MyApplication;

/**
 * Created by liu on 15/06/15.
 */
public final class ResouceUtils {
    private ResouceUtils(){}

    public static Resources getAppResources(){
        return MyApplication.getApplication().getResources();
    }
}

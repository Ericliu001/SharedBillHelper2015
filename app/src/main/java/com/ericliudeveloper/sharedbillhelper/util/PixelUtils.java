package com.ericliudeveloper.sharedbillhelper.util;

import android.content.Context;
import android.util.TypedValue;

import com.ericliudeveloper.sharedbillhelper.MyApplication;

/**
 * Created by liu on 15/06/15.
 */
public final class PixelUtils {
    private PixelUtils(){}



    public static int getPixelFromDp(int dp) {
        Context context = MyApplication.getApplication();

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}

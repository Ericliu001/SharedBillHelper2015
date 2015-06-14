package com.ericliudeveloper.sharedbillhelper.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ericliudeveloper.sharedbillhelper.MyApplication;

/**
 * Created by liu on 8/06/15.
 */
public final class Router {

    private Router(){}

    public static void startActivity(Activity activity, Class<?> dest, Bundle data) {
        Intent intent = new Intent(activity, dest);
        if (data != null) {
            intent.putExtras(data);
        }

        if (!(activity instanceof Activity)) {
            // System will throw an Exception if you try to call startActivity from outside an Activity without having the flag
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }


    public static void startActivityUsingApplicationContext(Class<?> dest, Bundle data) {
        Context context = MyApplication.getApplication();
        Intent intent = new Intent(context, dest);
        if (data != null) {
            intent.putExtras(data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

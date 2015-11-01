package com.ericliudeveloper.sharedbillhelper.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import de.greenrobot.event.EventBus;

/**
 * Created by ericliu on 1/11/2015.
 */
public final class PermissionsUtil {
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 11;

    private PermissionsUtil(){}

    public static void checkReadContactsPermission(Activity activity) {
        if (activity == null) {
            return;
        }
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            EventBus.getDefault().postSticky(new CustomEvents.ReadContactPermissionGrantedEvent());
        }
    }
}

package com.kremes.kremeswt.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import static com.kremes.kremeswt.utils.PowerSaverUtils.prepareIntentForWhiteListingOfBatteryOptimization;

/**
 * Created by Bota
 */

public class PermissionUtils {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 36;

    public static void checkAndRequestPermissions(Activity activity)
    {
        int receivesms = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS);
        int readsms = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
        int sendsms = ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receivesms != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (readsms != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (sendsms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }

        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(activity,listPermissionsNeeded.toArray(new
                    String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }

        //For Doze Mode
        Intent intent = prepareIntentForWhiteListingOfBatteryOptimization(activity, "com.kremes.kremeswt", false);
        if(intent != null)
            activity.startActivity(intent);
    }
}

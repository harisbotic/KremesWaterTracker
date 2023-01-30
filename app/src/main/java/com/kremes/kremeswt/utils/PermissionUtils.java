package com.kremes.kremeswt.utils;

import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import static com.kremes.kremeswt.utils.PowerSaverUtils.prepareIntentForWhiteListingOfBatteryOptimization;

/**
 * Created by Bota
 */

public class PermissionUtils {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 36;
    private static final int FILE_PICKER_ANDROID11_REQUEST_CODE = 38;

    public static void checkAndRequestPermissions(Activity activity)
    {
        int receivesms = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS);
        int readsms = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
        int sendsms = ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS);
        int readPhoneState = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        int readstorage = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
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
        if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (readstorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
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

        if (isAndroid11ReadFilesPermissionRequired())
            askForAndroid11ReadFilesPermission(activity);

    }

    private static boolean isAndroid11ReadFilesPermissionRequired() {
        return SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager();
    }
    @RequiresApi(Build.VERSION_CODES.R)
    private static void askForAndroid11ReadFilesPermission(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse(String.format("package:%s", activity.getApplicationContext().getPackageName())));
            activity.startActivityForResult(intent, FILE_PICKER_ANDROID11_REQUEST_CODE);
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            activity.startActivityForResult(intent, FILE_PICKER_ANDROID11_REQUEST_CODE);
        }
    }
}

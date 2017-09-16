package com.kremes.kremeswt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import com.kremes.kremeswt.model.SMSMsg;

import static com.kremes.kremeswt.utils.GeneralUtils.getGlobalSharedPref;

/**
 * Created by Bota
 */

public class SMSUtils {
    private static final String PrefKeyLastReadSMSDate = "kremeswt.pref_key_lastReadSMSID";

    public enum SMSTypes {
        REPORT, PAYMENT, REQUEST_PAYMENT, CHECK_BALANCE
    }

    public static void readAllSMS(Context context) {
        String[] projection = {"address", "body", "date"};
        String sortOrder = "date" + " DESC";
        Cursor c = context.getContentResolver().query(Uri.parse("content://sms/inbox"), projection, null, null, sortOrder);
        String lastReadSmsId = getLastReadSMSDate(context);
        SMSMsg smsMsg;

        if (c.moveToFirst()) {
            setLastReadSMSDate(context, c.getString(c.getColumnIndexOrThrow("date")));
            do {
                smsMsg = new SMSMsg();
                smsMsg.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                smsMsg.setMessage(c.getString(c.getColumnIndexOrThrow("body")));
                smsMsg.setDate(c.getString(c.getColumnIndexOrThrow("date")));

                if(!lastReadSmsId.equals(smsMsg.getDate())) {
                    // use msgData
                }
                else { break; }
            } while (c.moveToNext());
        }
        c.close();
    }

    public static String getLastReadSMSDate(Context context) {
        SharedPreferences sharedPref = getGlobalSharedPref(context);
        return sharedPref.getString(PrefKeyLastReadSMSDate, "");
    }

    private static void setLastReadSMSDate(Context context, String smsID) {
        SharedPreferences sharedPref = getGlobalSharedPref(context);
        sharedPref.edit().putString(PrefKeyLastReadSMSDate, smsID).commit();
    }

}

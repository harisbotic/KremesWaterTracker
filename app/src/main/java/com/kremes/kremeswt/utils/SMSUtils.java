package com.kremes.kremeswt.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;

import com.kremes.kremeswt.model.SMSMsg;

import static com.kremes.kremeswt.utils.GeneralUtils.getGlobalSharedPref;
import static com.kremes.kremeswt.utils.ReportUtils.createNewReport;

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
        String lastReadSmsDate = getLastReadSMSDate(context);
        SMSMsg smsMsg;

        if (c.moveToFirst()) {
            setLastReadSMSDate(context, c.getString(c.getColumnIndexOrThrow("date")));
            do {
                Log.d("MyTag", "stiglaDatuma: " + c.getString(c.getColumnIndexOrThrow("date")));
                smsMsg = new SMSMsg();
                smsMsg.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                smsMsg.setMessage(c.getString(c.getColumnIndexOrThrow("body")));
                smsMsg.setDate(c.getString(c.getColumnIndexOrThrow("date")));

                if(lastReadSmsDate.equals(smsMsg.getDate()))
                    break;

                String[] message = smsMsg.getMessage().trim().split(" ");

                if(!message[0].toLowerCase().equals("voda"))
                    continue;

                if(message.length != 3)
                    continue;

                long waterMeterNumber = 0;
                long waterSpent = 0;
                try {
                    waterMeterNumber = Long.parseLong(message[1]);
                    waterSpent = Long.parseLong(message[2]);
                } catch (Exception e) {
                    continue;
                }
                String phoneNumber = smsMsg.getAddress();

                createNewReport(context, waterMeterNumber, waterSpent, phoneNumber);

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

    public static void sendSuccessSMS(Context context, String phoneNumber, long waterMeterNumber, long waterSpent, String balance )
    {
        String message = "Uspjesno spremljene promjene za sat " + waterMeterNumber + "\n" +
                "Do sada ste potrosili " + waterSpent + " kubika vode\n" +
                "Trenutno stanje vaseg racuna je " + balance +" KM\n";
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,new Intent("SMS_SENT"), 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentIntent, null);
    }

    public static void sendFailSMS(Context context, String phoneNumber, String message)
    {
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,new Intent("SMS_SENT"), 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentIntent, null);
    }
}

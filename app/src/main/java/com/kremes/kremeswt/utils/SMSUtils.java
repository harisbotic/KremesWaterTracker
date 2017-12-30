package com.kremes.kremeswt.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import com.kremes.kremeswt.model.SMSMsg;

import java.util.ArrayList;

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
        String[] projection = {"_id", "address", "body", "date"};
        String sortOrder = "date" + " DESC";
        Cursor c = context.getContentResolver().query(Uri.parse("content://sms/inbox"), projection, null, null, sortOrder);
        int lastReadSmsId = getLastReadSMSId(context);
        SMSMsg smsMsg;

        if (c.moveToFirst()) {
            setLastReadSMSId(context, c.getInt(c.getColumnIndexOrThrow("_id")));
            do {
                smsMsg = new SMSMsg();

                smsMsg.setId(c.getInt(c.getColumnIndexOrThrow("_id")));
                smsMsg.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                smsMsg.setMessage(c.getString(c.getColumnIndexOrThrow("body")));
                smsMsg.setDate(c.getString(c.getColumnIndexOrThrow("date")));

                if(smsMsg.getId() <= lastReadSmsId)
                    break;

                System.out.println("Prosao:::");

                ArrayList<String> messages = new ArrayList<>();
                for (String msg: smsMsg.getMessage().trim().split(" ")) {
                    if(!msg.trim().equals(""))
                        messages.add(msg);
                }

                if(messages.size() == 0)
                    continue;

                if(!messages.get(0).toLowerCase().equals("voda"))
                    continue;

                if(messages.size() != 3)
                    continue;

                long waterMeterNumber = 0;
                long waterSpent = 0;
                try {
                    waterMeterNumber = Long.parseLong(messages.get(1));
                    waterSpent = Long.parseLong(messages.get(2));
                } catch (Exception e) {
                    continue;
                }
                String phoneNumber = smsMsg.getAddress();

                createNewReport(context, waterMeterNumber, waterSpent, phoneNumber);

            } while (c.moveToNext());
        }
        c.close();
    }

    public static int getLastReadSMSId(Context context) {
        SharedPreferences sharedPref = getGlobalSharedPref(context);
        return sharedPref.getInt(PrefKeyLastReadSMSDate, -1);
    }

    private static void setLastReadSMSId(Context context, int smsID) {
        SharedPreferences sharedPref = getGlobalSharedPref(context);
        sharedPref.edit().putInt(PrefKeyLastReadSMSDate, smsID).commit();
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

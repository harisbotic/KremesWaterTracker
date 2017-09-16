package com.kremes.kremeswt.receivers;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.widget.Toast;

import static com.kremes.kremeswt.utils.GeneralUtils.FormatDateMonth;
import static com.kremes.kremeswt.utils.SMSUtils.readAllSMS;

/**
 * Created by Bota
 */

public class SMSMessageReceiver extends BroadcastReceiver {
    private static final String SMS_EXTRA_NAME = "pdus";

    @Override
    public void onReceive(final Context context, Intent intent) {
        // Get the SMS map from Intent
        Bundle extras = intent.getExtras();

        String messages = "";

        if ( extras != null )
        {
            // Get received SMS array
            Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );

            // Get ContentResolver object for pushing encrypted SMS to the incoming folder
            ContentResolver contentResolver = context.getContentResolver();

            for ( int i = 0; i < smsExtra.length; ++i )
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);

                String body = sms.getMessageBody().toString();
                String address = sms.getOriginatingAddress();
                String datemonth = FormatDateMonth(System.currentTimeMillis());

                messages += "SMS od " + address + " :\n";
                messages += body + "\n";
            }

            // Display SMS message
            Toast.makeText( context, messages, Toast.LENGTH_SHORT ).show();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                readAllSMS(context);
            }
        }, 4000);

        // WARNING!!!
        // If you uncomment the next line then system will never know for the message.
        // Be careful!
        // this.abortBroadcast();
    }
}

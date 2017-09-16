package com.kremes.kremeswt.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by Bota
 */

public class GeneralUtils {
    private static final String GlobalSharedPrefKey = "ocm.kremes.kremeswt";

    public static String FormatDateMonth(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d/%02d", year, month);
    }

    public static SharedPreferences getGlobalSharedPref(Context context) {
        return context.getSharedPreferences(GlobalSharedPrefKey, Context.MODE_PRIVATE);
    }
}

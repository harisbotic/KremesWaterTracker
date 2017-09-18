package com.kremes.kremeswt.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Bota
 */

public class GeneralUtils {
    private static final String GlobalSharedPrefKey = "ocm.kremes.kremeswt";

    public static String FormatDateMonth(int addMonths) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, addMonths);
        return FormatDateMonth(c);
    }

    public static String FormatDateMonth(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
        return FormatDateMonth(c);
    }

    public static String FormatDateMonth(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d/%02d", year, month);
    }

    public static SharedPreferences getGlobalSharedPref(Context context) {
        return context.getSharedPreferences(GlobalSharedPrefKey, Context.MODE_PRIVATE);
    }

    public static String formatCapitalFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String getGregorianMonthName(Context context, int month) {
        String[] months = getMonthsByFill(context, "month%02d");
        return months[month];
    }

    private static String[] getMonthsByFill(Context context, String fill) {
        String[] ret = new String[12];
        for (int i=0; i<12; i++) {
            ret[i] = GeneralUtils.getStringByKey(String.format(Locale.US, fill, i + 1), context);
        }
        return ret;
    }

    public static String getStringByKey(String key, Context context) {
        int id = context.getResources().getIdentifier(key, "string", context.getPackageName());
        return context.getResources().getString(id);
    }
}

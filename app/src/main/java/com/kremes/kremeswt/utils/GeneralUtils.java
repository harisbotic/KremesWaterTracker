package com.kremes.kremeswt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        return FormatDateMonth(month, year);
    }

    public static String FormatDateMonth(int month, int year) {
        return String.format("%04d/%02d", year, month);
    }

    public static int getMonthNumberFromDateMonth(String dateMonth) {
        return Integer.parseInt(dateMonth.substring(5,7));
    }

    public static String formatNormalDate(long milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    public static SharedPreferences getGlobalSharedPref(Context context) {
        return context.getSharedPreferences(GlobalSharedPrefKey, Context.MODE_PRIVATE);
    }

    public static String formatCapitalFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String formatCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public static String[] getAllGregorianMonths(Context context) {
        return getMonthsByFill(context, "month%02d");
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

    public static void fillGregorianMonths(Spinner spinner, Context context) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, getAllGregorianMonths(context));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        Calendar gregorian = new GregorianCalendar();
        spinner.setSelection(gregorian.get(Calendar.MONTH));
    }

    public static void fillGregorianYears(Spinner spinner, Context context) {
        String[] years = new String[200];
        int startYear = Calendar.getInstance().get(Calendar.YEAR);
        int yearIterator = startYear;
        for (int i=0; i<200; i++) {
            years[i] = String.valueOf(yearIterator++);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, years);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(dataAdapter.getPosition(String.valueOf(startYear)));
    }

    public static String getStringByKey(String key, Context context) {
        int id = context.getResources().getIdentifier(key, "string", context.getPackageName());
        return context.getResources().getString(id);
    }
}

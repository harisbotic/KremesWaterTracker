package com.kremes.kremeswt.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;
import com.kremes.kremeswt.entity.Fee;
import com.kremes.kremeswt.entity.Payment;
import com.kremes.kremeswt.entity.Report;
import com.kremes.kremeswt.views.ReportCard;

import java.util.ArrayList;
import java.util.List;

import static com.kremes.kremeswt.utils.WaterFeeUtils.getFeeByDateMonth;

/**
 * Created by Bota
 */

public class CitizenUtils {

    public static String formatUsername(String fullName) {
        String name = fullName.split(" ")[0];
        String surname = fullName.split(" ")[1];
        return formatUsername(name, surname);
    }

    public static String formatUsername(String name, String surname) {
        return (name + surname).toLowerCase();
    }

    public static String getCitizenFullName(Citizen citizen) {
        return citizen.getFirstName() + " " + citizen.getLastName();
    }

    public static String formatCitizenPhoneNumer(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("\\s+","");
        if(phoneNumber.startsWith("0") && !phoneNumber.startsWith("00"))
            phoneNumber = "+387" + phoneNumber.substring(1);

        return phoneNumber;
    }

    public static List<String> convertCitizenToNamesOnly(List<Citizen> citizenList) {
        ArrayList<String> names = new ArrayList<String>();
        for (Citizen citizen : citizenList) {
            names.add(citizen.getFirstName() + " " + citizen.getLastName());
        }
        return names;
    }

    public static void displaysearchCitizensDialog(final Context context, final TextView textView) {
        new AsyncTask<Void, Void, List<Citizen>>() {
            protected List<Citizen> doInBackground(Void... v) {
                return KremesDatabase.getAppDatabase(context).citizenDao().getAll();

            }

            protected void onPostExecute(List<Citizen> allCitizens) {
                new MaterialDialog.Builder(context)
                        .title("Odaberi Korisnika")
                        .items(convertCitizenToNamesOnly(allCitizens))
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                textView.setText(text);
                            }
                        })
                        .show();
            }
        }.execute();
    }

    public static void updateCitizenStatistic(final Context context, final String citizenUsername) {
        new AsyncTask<Void, Void, Citizen>() {
            protected Citizen doInBackground(Void... voids) {
                try {
                    List<Fee> allFees = KremesDatabase.getAppDatabase(context).feeDao().getAll();
                    List<Report> allReports = KremesDatabase.getAppDatabase(context).reportDao().getAllForUsername(citizenUsername);
                    List<Payment> allPayments = KremesDatabase.getAppDatabase(context).paymentDao().getAllForUsername(citizenUsername);
                    double lastMonthReportAmount = 0;
                    double newBalance = 0;
                    for (Report report: allReports) {
                        Fee fee = getFeeByDateMonth(allFees, report.getDateMonth());
                        if(fee != null) {
                            newBalance -= fee.getPrice() * (report.getWaterAmount() - lastMonthReportAmount);
                        }
                    }
                    for (Payment payment:allPayments) {
                        newBalance += payment.getAmount();
                    }

                    Citizen citizen = KremesDatabase.getAppDatabase(context).citizenDao().getByUsername(citizenUsername);
                    citizen.setBalance(newBalance);

                    KremesDatabase.getAppDatabase(context).citizenDao().update(citizen);
                    return citizen;
                } catch (Exception e) {
                    return null;
                }
            }

            protected void onPostExecute(Citizen citizen) {
                if (citizen != null) {
                    //Send SMS
                }
            }
        }.execute();
    }
}

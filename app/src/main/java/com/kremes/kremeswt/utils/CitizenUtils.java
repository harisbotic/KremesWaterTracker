package com.kremes.kremeswt.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;

import java.util.ArrayList;
import java.util.List;

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

    public static void getUsernameByName(final Context context, final String name, final TextView textView) {
        new AsyncTask<Void, Void, Citizen>() {
            protected Citizen doInBackground(Void... v) {
                String first = name.split(" ")[0];
                String last = name.split(" ")[1];
                return KremesDatabase.getAppDatabase(context).citizenDao().getByName(first, last);
            }

            protected void onPostExecute(Citizen citizen) {
                textView.setText(citizen.getUsername());
            }
        }.execute();
    }
}

package com.kremes.kremeswt.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Report;
import com.kremes.kremeswt.views.ReportCard;

import static com.kremes.kremeswt.utils.CitizenUtils.displaysearchCitizensDialog;
import static com.kremes.kremeswt.utils.CitizenUtils.formatUsername;
import static com.kremes.kremeswt.utils.GeneralUtils.FormatDateMonth;

/**
 * Created by Bota
 */

public class ReportUtils {

    public static void displayNewReportDialog(final Context context, final String citizenFullName, final ReportCard reportCard) {
        final MaterialDialog dialogAddReport = new MaterialDialog.Builder(context)
                .title("Novi Izvještaj")
                .customView(R.layout.dialog_report_add, false)
                .autoDismiss(true)
                .cancelable(false)
                .negativeText("ODUSTANI")
                .positiveText("DODAJ IZVJEŠTAJ")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        TextView citizenName = (TextView) dialog.findViewById(R.id.dialogCitizenSelectedName);
                        EditText newAmount = (EditText) dialog.findViewById(R.id.dialogReportNewAmount);

                        if(citizenName.getText().toString().equals("")) {
                            Toast.makeText(context, "Morate Odabrati Korisnika", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Report newReport = new Report(formatUsername(citizenName.getText().toString()),
                                FormatDateMonth(-1),
                                Long.parseLong(newAmount.getText().toString()));

                        createNewReport(context, newReport, reportCard);
                    }
                })
                .build();

        ImageButton searchCitizen = (ImageButton) dialogAddReport.findViewById(R.id.dialogCitizenSearch);
        searchCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaysearchCitizensDialog(context, (TextView) dialogAddReport.findViewById(R.id.dialogCitizenSelectedName));
            }
        });

        TextView citizenName = (TextView) dialogAddReport.findViewById(R.id.dialogCitizenSelectedName);
        citizenName.setText(citizenFullName);

        dialogAddReport.show();
    }

    private static void createNewReport(final Context context, final Report newReport, final ReportCard reportCard) {
        new AsyncTask<Report, Void, Boolean>() {
            protected Boolean doInBackground(Report... newReports) {
                try {
                    return KremesDatabase.getAppDatabase(context).reportDao().insert(newReports[0]) >= 1;
                } catch (Exception e) {
                    return false;
                }
            }

            protected void onPostExecute(Boolean r) {
                if (r == true) {
                    if(reportCard != null) {
                        reportCard.citizen.setWaterAmountLastMonth(newReport.getWaterAmount());
                        reportCard.updateUI();
                    }
                        Toast.makeText(context, "Izvještaj uspješno dodan", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(context, "Greška, Izvještaj vec unesen za ovog korisnika", Toast.LENGTH_LONG).show();

            }
        }.execute(newReport);
    }
}
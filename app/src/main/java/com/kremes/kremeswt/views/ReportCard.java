package com.kremes.kremeswt.views;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Report;
import com.kremes.kremeswt.model.CitizenWithReport;

import java.util.Calendar;

import static com.kremes.kremeswt.utils.CitizenUtils.getCitizenFullName;
import static com.kremes.kremeswt.utils.GeneralUtils.FormatDateMonth;
import static com.kremes.kremeswt.utils.GeneralUtils.getGregorianMonthName;
import static com.kremes.kremeswt.utils.GeneralUtils.getMonthNumberFromDateMonth;
import static com.kremes.kremeswt.utils.ReportUtils.displayNewReportDialog;

/**
 * Created by Bota
 */

public class ReportCard extends RelativeLayout implements CardView.OnClickListener {
    Context context;
    KremesDatabase db;

    TextView reportCardName;
    TextView reportCardAmount;

    CitizenWithReport citizen;

    public ReportCard(Context context, CitizenWithReport citizen) {
        super(context);
        this.context = context;
        this.citizen = citizen;
        this.db = KremesDatabase.getAppDatabase(context);

        init();
        setOnClickListener(this);
    }

    private void init() {
        inflate(getContext(), R.layout.element_report_card, this);
        reportCardName = findViewById(R.id.reportCardName);
        reportCardName.setText(getCitizenFullName(citizen));
        reportCardAmount = findViewById(R.id.reportCardAmount);
        updateUI();
    }

    @Override
    public void onClick(View v) {
        if(citizen.getWaterAmountLastMonth() <= 0)
            displayNewReportDialog(context, getCitizenFullName(citizen));
        else
            showDeleteReportDialog();


    }

    private void updateUI() {
        int monthNumber = getMonthNumberFromDateMonth(FormatDateMonth(Calendar.getInstance()));
        reportCardAmount.setText(getGregorianMonthName(context, monthNumber) + ": " + citizen.getWaterAmountLastMonth());
    }

    private void showDeleteReportDialog() {
        new MaterialDialog.Builder(context)
                .title("Brisanje zadnjeg izvještaja?")
                .content("Da li sigurno želite izbrisati zadnji izvještaj od " + getCitizenFullName(citizen))
                .positiveText("NE ŽELIM")
                .negativeText("IZBRIŠI")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteThisReport();
                    }
                })
                .show();
    }

    private void deleteThisReport() {
        new AsyncTask<Void, Void, Boolean>() {
            protected Boolean doInBackground(Void... params) {
                Report report = db.reportDao().findByUsernameAndDateMonth(citizen.getUsername(), FormatDateMonth(-1));
                return db.reportDao().delete(report) >= 1;
            }

            protected void onPostExecute(Boolean r) {
                citizen.setWaterAmountLastMonth(0);
                updateUI();
                Toast.makeText(context, "Izvještaja Izbrisan", Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

}
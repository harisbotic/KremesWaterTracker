package com.kremes.kremeswt.views;

import android.content.Context;
import android.graphics.Color;
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

import static com.kremes.kremeswt.utils.CitizenUtils.getCitizenFullName;
import static com.kremes.kremeswt.utils.CitizenUtils.updateCitizenStatistic;
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

    CardView reportCardLayout;

    TextView reportCardName;
    TextView reportCardLastMonthAmount;
    TextView reportCardLastTwoMonthAmount;

    public CitizenWithReport citizen;

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
        reportCardLayout = findViewById(R.id.reportCardLayout);
        reportCardName = findViewById(R.id.reportCardName);
        reportCardName.setText(getCitizenFullName(citizen));
        reportCardLastMonthAmount = findViewById(R.id.reportCardLastMonthAmount);
        reportCardLastTwoMonthAmount = findViewById(R.id.reportCardLastTwoMonthAmount);
        updateUI();
    }

    @Override
    public void onClick(View v) {
        if(citizen.getWaterAmountLastMonth() <= 0)
            displayNewReportDialog(context, getCitizenFullName(citizen), this);
        else
            showDeleteReportDialog();


    }

    public void updateUI() {
        int monthNumber = getMonthNumberFromDateMonth(FormatDateMonth(-1));
        int monthNumber2 = getMonthNumberFromDateMonth(FormatDateMonth(-2));

        if(citizen.getWaterAmountLastMonth() <=0) {
            reportCardLayout.setBackgroundColor(Color.parseColor("#EF5350"));
            reportCardLastMonthAmount.setText("Nije uneseno za " + getGregorianMonthName(context, monthNumber));
        } else {
            reportCardLayout.setBackgroundColor(Color.parseColor("#26A69A"));
            reportCardLastMonthAmount.setText(getGregorianMonthName(context, monthNumber) + ": " + citizen.getWaterAmountLastMonth());
        }

        reportCardLastTwoMonthAmount.setText(getGregorianMonthName(context, monthNumber2) + ": " + citizen.getWaterAmountLastTwoMonth());
    }

    private void showDeleteReportDialog() {
        new MaterialDialog.Builder(context)
                .title("Brisanje zadnjeg izvještaja?")
                .content("Da li sigurno želite izbrisati zadnji izvještaj od " + getCitizenFullName(citizen))
                .positiveText("IZBRIŠI")
                .negativeText("NE ŽELIM")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
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
                updateCitizenStatistic(context, citizen.getUsername());
                Toast.makeText(context, "Izvještaja Izbrisan", Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

}
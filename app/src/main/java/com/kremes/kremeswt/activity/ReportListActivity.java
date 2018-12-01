package com.kremes.kremeswt.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.model.CitizenWithReport;
import com.kremes.kremeswt.views.ReportCard;

import java.util.ArrayList;
import java.util.List;

import static com.kremes.kremeswt.utils.GeneralUtils.FormatDateMonth;
import static com.kremes.kremeswt.utils.GeneralUtils.getGregorianMonthName;
import static com.kremes.kremeswt.utils.GeneralUtils.getMonthNumberFromDateMonth;
import static com.kremes.kremeswt.utils.ReportUtils.displayNewReportDialog;

public class ReportListActivity extends AppCompatActivity {

    LinearLayout reportCardHolder;
    TextView tvTotalWaterSpent;

    KremesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        db = KremesDatabase.getAppDatabase(ReportListActivity.this);
        reportCardHolder = findViewById(R.id.reportCardHolder);
        tvTotalWaterSpent = findViewById(R.id.tvTotalWaterSpent);

        listAllReports();

        FloatingActionButton fab = findViewById(R.id.fabReportAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNewReportDialog(ReportListActivity.this, "", null);
            }
        });
    }

    private void listAllReports() {
        new AsyncTask<Void, Void, List<CitizenWithReport>>() {
            protected List<CitizenWithReport> doInBackground(Void... v) {
                return db.citizenDao().getAllWithReports(FormatDateMonth(-1), FormatDateMonth(-2));

            }

            protected void onPostExecute(List<CitizenWithReport> allCitizens) {
                long totalWaterSpent = 0;
                long lastMonthWaterSpent = 0;
                List<CitizenWithReport> newReportList = sortReports(allCitizens);
                for (CitizenWithReport citizen: newReportList) {
                    long waterSpent = citizen.getWaterAmountLastMonth() > 0? citizen.getWaterAmountLastMonth() : 0;
                    totalWaterSpent += waterSpent > 0? waterSpent : citizen.getWaterAmountLastTwoMonth();
                    if(waterSpent > 0) {
                        lastMonthWaterSpent += waterSpent - (citizen.getWaterAmountLastTwoMonth() > 0 ? citizen.getWaterAmountLastTwoMonth() : 0);
                    }
                    reportCardHolder.addView(new ReportCard(ReportListActivity.this, citizen));
                }
                int monthNumber = getMonthNumberFromDateMonth(FormatDateMonth(-1));
                String lastMonthName = getGregorianMonthName(ReportListActivity.this, monthNumber);
                tvTotalWaterSpent.setText("Potrošnja za " + lastMonthName + ": " + lastMonthWaterSpent + "m3 (U: " + totalWaterSpent + "m3)");
            }
        }.execute();
    }

    private List<CitizenWithReport> sortReports(List<CitizenWithReport> reportList) {
        ArrayList<CitizenWithReport> greenReports = new ArrayList<>();
        ArrayList<CitizenWithReport> redReports = new ArrayList<>();
        for (CitizenWithReport report: reportList) {
            if(report.getWaterAmountLastMonth() > 0 || report.getWaterSpent() == -1)
                greenReports.add(report);
            else
                redReports.add(report);
        }
        redReports.addAll(greenReports);
        return redReports;
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

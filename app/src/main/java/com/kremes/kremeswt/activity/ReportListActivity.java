package com.kremes.kremeswt.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.model.CitizenWithReport;
import com.kremes.kremeswt.views.ReportCard;

import java.util.List;

import static com.kremes.kremeswt.utils.GeneralUtils.FormatDateMonth;
import static com.kremes.kremeswt.utils.ReportUtils.displayNewReportDialog;

public class ReportListActivity extends AppCompatActivity {

    LinearLayout reportCardHolder;

    KremesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        db = KremesDatabase.getAppDatabase(ReportListActivity.this);
        listAllReports();

        reportCardHolder = findViewById(R.id.reportCardHolder);

        FloatingActionButton fab = findViewById(R.id.fabReportAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNewReportDialog(ReportListActivity.this, "");
            }
        });
    }

    private void listAllReports() {
        new AsyncTask<Void, Void, List<CitizenWithReport>>() {
            protected List<CitizenWithReport> doInBackground(Void... v) {
                return db.citizenDao().getAllWithReports(FormatDateMonth(-1), FormatDateMonth(-2));

            }

            protected void onPostExecute(List<CitizenWithReport> allCitizens) {
                for (CitizenWithReport citizen: allCitizens) {
                    reportCardHolder.addView(new ReportCard(ReportListActivity.this, citizen));
                }
            }
        }.execute();
    }
}

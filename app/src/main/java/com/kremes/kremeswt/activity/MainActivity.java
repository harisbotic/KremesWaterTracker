package com.kremes.kremeswt.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;

import java.util.List;

import static com.kremes.kremeswt.utils.PaymentUtils.displayNewPaymentDialog;
import static com.kremes.kremeswt.utils.PermissionUtils.checkAndRequestPermissions;
import static com.kremes.kremeswt.utils.ReportUtils.displayNewReportDialog;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    Button btnStatistics;
    Button btnNewReport;
    Button btnNewPayment;
    Button btnChangeWaterFee;
    Button btnSendSMSToEveryone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        checkAndRequestPermissions(MainActivity.this);

        btnStatistics = findViewById(R.id.btnStatistics);
        btnStatistics.setOnClickListener(this);
        btnNewReport = findViewById(R.id.btnNewReport);
        btnNewReport.setOnClickListener(this);
        btnNewPayment = findViewById(R.id.btnNewPayment);
        btnNewPayment.setOnClickListener(this);
        btnChangeWaterFee = findViewById(R.id.btnChangeWaterFee);
        btnChangeWaterFee.setOnClickListener(this);
        btnSendSMSToEveryone = findViewById(R.id.btnSendSMSToEveryone);
        btnSendSMSToEveryone.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        KremesDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //CREATE FEES
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStatistics:
                startActivity(new Intent(MainActivity.this, MenuStatisticsActivity.class));
                break;

            case R.id.btnNewReport:
                displayNewReportDialog(MainActivity.this, "", null);
                break;

            case R.id.btnNewPayment:
                displayNewPaymentDialog(MainActivity.this, "", null);
                break;

            case R.id.btnChangeWaterFee:
                startActivity(new Intent(MainActivity.this, WaterFeeActivity.class));
                break;

            case R.id.btnSendSMSToEveryone:
                sendSMSToEveryone();
                break;
        }
    }

    private void sendSMSToEveryone() {
        new AsyncTask<Void, Void, List<Citizen>>() {
            protected List<Citizen> doInBackground(Void... v) {
                return KremesDatabase.getAppDatabase(MainActivity.this).citizenDao().getAll();

            }

            protected void onPostExecute(List<Citizen> allCitizens) {
                String phoneNumbers = "";
                for (Citizen citizen: allCitizens) {
                    phoneNumbers = phoneNumbers + citizen.getPhoneNumber() + ",";
                }
                phoneNumbers = phoneNumbers.substring(0, phoneNumbers.length()-1);
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumbers)));
            }
        }.execute();
    }
}

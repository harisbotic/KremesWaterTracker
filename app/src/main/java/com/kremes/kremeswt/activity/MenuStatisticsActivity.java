package com.kremes.kremeswt.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kremes.kremeswt.R;

public class MenuStatisticsActivity extends AppCompatActivity implements Button.OnClickListener {

    Button btnListCitizenStatus;
    Button btnListReport;
    Button btnListPayment;
    Button btnListAddCitizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_statistics);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        btnListCitizenStatus = findViewById(R.id.btnListCitizenStatus);
        btnListCitizenStatus.setOnClickListener(this);
        btnListReport = findViewById(R.id.btnListReport);
        btnListReport.setOnClickListener(this);
        btnListAddCitizen = findViewById(R.id.btnListAddCitizen);
        btnListAddCitizen.setOnClickListener(this);
        btnListPayment = findViewById(R.id.btnListPayment);
        btnListPayment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnListCitizenStatus:
                startActivity(new Intent(MenuStatisticsActivity.this, BalanceListActivity.class));
                break;

            case R.id.btnListReport:
                startActivity(new Intent(MenuStatisticsActivity.this, ReportListActivity.class));
                break;

            case R.id.btnListPayment:
                startActivity(new Intent(MenuStatisticsActivity.this, PaymentListActivity.class));
                break;

            case R.id.btnListAddCitizen:
                startActivity(new Intent(MenuStatisticsActivity.this, CitizenListActivity.class));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

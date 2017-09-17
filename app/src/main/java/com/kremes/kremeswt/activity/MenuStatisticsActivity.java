package com.kremes.kremeswt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kremes.kremeswt.R;

public class MenuStatisticsActivity extends AppCompatActivity implements Button.OnClickListener {

    Button btnListCitizenStatus;
    Button btnListAddReport;
    Button btnListAddCitizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_statistics);

        btnListCitizenStatus = findViewById(R.id.btnListCitizenStatus);
        btnListCitizenStatus.setOnClickListener(this);
        btnListAddReport = findViewById(R.id.btnListAddReport);
        btnListAddReport.setOnClickListener(this);
        btnListAddCitizen = findViewById(R.id.btnListAddCitizen);
        btnListAddCitizen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnListCitizenStatus:
                //startActivity(new Intent(MenuStatisticsActivity.this, MenuStatisticsActivity.class));
                break;

            case R.id.btnListAddReport:
                //startActivity(new Intent(MainActivity.this, MenuStatisticsActivity.class));
                break;

            case R.id.btnListAddCitizen:
                //startActivity(new Intent(MainActivity.this, MenuStatisticsActivity.class));
                break;
        }
    }
}

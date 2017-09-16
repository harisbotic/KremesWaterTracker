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

        btnListCitizenStatus = (Button) findViewById(R.id.btnListCitizenStatus);
        btnListAddReport = (Button) findViewById(R.id.btnListAddReport);
        btnListAddCitizen = (Button) findViewById(R.id.btnListAddCitizen);
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

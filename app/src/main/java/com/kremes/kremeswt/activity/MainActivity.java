package com.kremes.kremeswt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{

    Button btnStatistics;
    Button btnNewPayment;
    Button btnChangeWaterFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStatistics = findViewById(R.id.btnStatistics);
        btnStatistics.setOnClickListener(this);
        btnNewPayment = findViewById(R.id.btnNewPayment);
        btnNewPayment.setOnClickListener(this);
        btnChangeWaterFee = findViewById(R.id.btnChangeWaterFee);
        btnChangeWaterFee.setOnClickListener(this);



        //DatabaseInitializer.populateAsync(KremesDatabase.getAppDatabase(this));

    }

    @Override
    protected void onDestroy() {
        KremesDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStatistics:
                startActivity(new Intent(MainActivity.this, MenuStatisticsActivity.class));
                break;

            case R.id.btnNewPayment:
                //startActivity(new Intent(MainActivity.this, MenuStatisticsActivity.class));
                break;

            case R.id.btnChangeWaterFee:
                startActivity(new Intent(MainActivity.this, WaterFeeActivity.class));
                break;
        }
    }
}

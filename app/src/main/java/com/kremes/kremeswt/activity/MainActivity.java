package com.kremes.kremeswt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DatabaseInitializer.populateAsync(KremesDatabase.getAppDatabase(this));

    }

    @Override
    protected void onDestroy() {
        KremesDatabase.destroyInstance();
        super.onDestroy();
    }
}

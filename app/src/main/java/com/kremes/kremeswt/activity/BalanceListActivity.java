package com.kremes.kremeswt.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;
import com.kremes.kremeswt.views.BalanceCard;

import java.util.List;

public class BalanceListActivity extends AppCompatActivity {

    LinearLayout balanceCardHolder;
    KremesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_list);
        db = KremesDatabase.getAppDatabase(BalanceListActivity.this);
        balanceCardHolder = findViewById(R.id.balanceCardHolder);
        listAllCitizens();

    }

    private void listAllCitizens() {
        new AsyncTask<Void, Void, List<Citizen>>() {
            protected List<Citizen> doInBackground(Void... v) {
                return db.citizenDao().getAll();

            }

            protected void onPostExecute(List<Citizen> allCitizens) {
                for (Citizen citizen: allCitizens) {
                    balanceCardHolder.addView(new BalanceCard(BalanceListActivity.this, citizen));
                }
            }
        }.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

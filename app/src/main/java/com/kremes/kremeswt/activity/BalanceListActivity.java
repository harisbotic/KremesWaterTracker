package com.kremes.kremeswt.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;
import com.kremes.kremeswt.model.CitizenWithReport;
import com.kremes.kremeswt.views.BalanceCard;

import java.util.ArrayList;
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
                List<Citizen> newCitizenList = sortCitizensByBalance(allCitizens);
                for (Citizen citizen: newCitizenList) {
                    balanceCardHolder.addView(new BalanceCard(BalanceListActivity.this, citizen));
                }
            }
        }.execute();
    }

    private List<Citizen> sortCitizensByBalance(List<Citizen> citizenList) {
        ArrayList<Citizen> positiveBalances = new ArrayList<>();
        ArrayList<Citizen> negativeBalances = new ArrayList<>();
        for (Citizen citizen: citizenList) {
            if(citizen.getBalance() >= 0)
                positiveBalances.add(citizen);
            else
                negativeBalances.add(citizen);
        }
        negativeBalances.addAll(positiveBalances);
        return negativeBalances;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

package com.kremes.kremeswt.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;
import com.kremes.kremeswt.views.BalanceCard;

import java.util.ArrayList;
import java.util.List;

public class BalanceListActivity extends AppCompatActivity {

    LinearLayout balanceCardHolder;
    TextView tvTotalInMinus;
    TextView tvTotalInPlus;
    KremesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_list);
        db = KremesDatabase.getAppDatabase(BalanceListActivity.this);
        balanceCardHolder = findViewById(R.id.balanceCardHolder);
        tvTotalInPlus = findViewById(R.id.tvTotalInPlus);
        tvTotalInMinus = findViewById(R.id.tvTotalInMinus);
        listAllCitizens();

    }

    private void listAllCitizens() {
        new AsyncTask<Void, Void, List<Citizen>>() {
            protected List<Citizen> doInBackground(Void... v) {
                return db.citizenDao().getAll();

            }

            protected void onPostExecute(List<Citizen> allCitizens) {
                List<Citizen> newCitizenList = sortCitizensByBalance(allCitizens);
                double totalInMinus = 0;
                double totalInPlus = 0;
                for (Citizen citizen: newCitizenList) {
                    balanceCardHolder.addView(new BalanceCard(BalanceListActivity.this, citizen));

                    if(citizen.getBalance() > 0)
                        totalInPlus += citizen.getBalance();
                    else
                        totalInMinus -= citizen.getBalance();
                }
                tvTotalInMinus.setText(tvTotalInMinus.getText().toString() + "-" + totalInMinus+"KM");
                tvTotalInPlus.setText(tvTotalInPlus.getText().toString() + "+" + totalInPlus+"KM");
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

package com.kremes.kremeswt.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.model.CitizenWithPayment;
import com.kremes.kremeswt.views.PaymentCard;

import java.util.Calendar;
import java.util.List;

import static com.kremes.kremeswt.utils.GeneralUtils.FormatDateMonth;
import static com.kremes.kremeswt.utils.GeneralUtils.fillGregorianMonths;
import static com.kremes.kremeswt.utils.GeneralUtils.fillGregorianYears;
import static com.kremes.kremeswt.utils.PaymentUtils.displayNewPaymentDialog;

public class PaymentListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerMonth;
    Spinner spinnerYear;

    TextView ukupno;

    LinearLayout paymentCardHolder;
    KremesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        db = KremesDatabase.getAppDatabase(PaymentListActivity.this);

        paymentCardHolder = findViewById(R.id.paymentCardHolder);
        spinnerMonth = findViewById(R.id.filter_gregorian_month);
        spinnerYear = findViewById(R.id.filter_gregorian_year);
        ukupno = findViewById(R.id.ukupno);

        fillGregorianMonths(spinnerMonth, PaymentListActivity.this);
        fillGregorianYears(spinnerYear, PaymentListActivity.this);

        spinnerMonth.setOnItemSelectedListener(this);
        spinnerYear.setOnItemSelectedListener(this);

        listAllPayments(FormatDateMonth(Calendar.getInstance()));

        FloatingActionButton fab = findViewById(R.id.fabPaymentAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNewPaymentDialog(PaymentListActivity.this, "", PaymentListActivity.this);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        String dateMonth = FormatDateMonth(spinnerMonth.getSelectedItemPosition(),
                                           Integer.parseInt((String)spinnerYear.getSelectedItem()));
        listAllPayments(dateMonth);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    public void listAllPayments(String dateMonth) {
        new AsyncTask<String, Void, List<CitizenWithPayment>>() {
            protected List<CitizenWithPayment> doInBackground(String... dateMonth) {
                return db.citizenDao().getAllWithPayment(dateMonth[0]);

            }

            protected void onPostExecute(List<CitizenWithPayment> allCitizens) {
                paymentCardHolder.removeAllViews();double number = 0;

                for (CitizenWithPayment citizen: allCitizens) {

                    number += citizen.getAmount();
                    paymentCardHolder.addView(new PaymentCard(PaymentListActivity.this, citizen));
                }
                ukupno.setText("Ukupno: " + number + "KM");
            }
        }.execute(dateMonth);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

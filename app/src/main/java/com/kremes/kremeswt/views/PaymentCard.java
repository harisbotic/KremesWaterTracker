package com.kremes.kremeswt.views;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Payment;
import com.kremes.kremeswt.model.CitizenWithPayment;

import static com.kremes.kremeswt.utils.CitizenUtils.getCitizenFullName;
import static com.kremes.kremeswt.utils.CitizenUtils.updateCitizenStatistic;
import static com.kremes.kremeswt.utils.GeneralUtils.formatNormalDate;

/**
 * Created by Bota
 */

public class PaymentCard extends RelativeLayout implements CardView.OnClickListener {
    Context context;
    KremesDatabase db;

    TextView paymentCardName;
    TextView paymentCardAmount;
    TextView paymentCardTimestamp;

    CitizenWithPayment citizen;

    public PaymentCard(Context context, CitizenWithPayment citizen) {
        super(context);
        this.context = context;
        this.citizen = citizen;
        this.db = KremesDatabase.getAppDatabase(context);

        init();
        setOnClickListener(this);
    }

    private void init() {
        inflate(getContext(), R.layout.element_payment_card, this);
        paymentCardName = findViewById(R.id.paymentCardName);
        paymentCardName.setText(getCitizenFullName(citizen));
        paymentCardAmount = findViewById(R.id.paymentCardAmount);
        paymentCardAmount.setText("+" + citizen.getAmount() + " KM");
        paymentCardTimestamp = findViewById(R.id.paymentCardTimestamp);
        paymentCardTimestamp.setText(formatNormalDate(citizen.getDatePaid()));
    }

    @Override
    public void onClick(View v) {
        showDeletePaymentDialog();
    }

    private void showDeletePaymentDialog() {
        new MaterialDialog.Builder(context)
                .title("Brisanje uplate?")
                .content("Da li sigurno želite izbrisati uplatu od " + getCitizenFullName(citizen))
                .positiveText("IZBRIŠI")
                .negativeText("NE ŽELIM")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteThisPayment();
                    }
                })
                .show();
    }

    private void deleteThisPayment() {
        new AsyncTask<Void, Void, Boolean>() {
            protected Boolean doInBackground(Void... params) {
                Payment payment = db.paymentDao().getById(citizen.getId());
                return db.paymentDao().delete(payment) >= 1;
            }

            protected void onPostExecute(Boolean r) {
                setVisibility(GONE);
                updateCitizenStatistic(context, citizen.getUsername());
                Toast.makeText(context, "Uplata Izbrisana", Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

}
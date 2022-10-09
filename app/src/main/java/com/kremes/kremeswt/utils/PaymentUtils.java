package com.kremes.kremeswt.utils;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.activity.PaymentListActivity;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Payment;

import java.util.Calendar;

import static com.kremes.kremeswt.utils.CitizenUtils.displaysearchCitizensDialog;
import static com.kremes.kremeswt.utils.CitizenUtils.formatUsername;
import static com.kremes.kremeswt.utils.CitizenUtils.updateCitizenStatistic;
import static com.kremes.kremeswt.utils.GeneralUtils.FormatDateMonth;

/**
 * Created by Bota
 */

public class PaymentUtils {

    public static void displayNewPaymentDialog(final Context context, final String citizenFullName, final PaymentListActivity activity) {
        final MaterialDialog dialogAddPayment = new MaterialDialog.Builder(context)
                .title("Nova Uplata")
                .customView(R.layout.dialog_payment_add, false)
                .autoDismiss(true)
                .cancelable(false)
                .negativeText("ODUSTANI")
                .positiveText("NAPRAVI UPLATU")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        TextView citizenName = (TextView) dialog.findViewById(R.id.dialogCitizenSelectedName);
                        EditText newAmount = (EditText) dialog.findViewById(R.id.dialogPaymentNewAmount);

                        if(citizenName.getText().toString().equals("")) {
                            Toast.makeText(context, "Morate Odabrati Korisnika", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Payment newPayment = new Payment(formatUsername(citizenName.getText().toString()),
                                FormatDateMonth(0),
                                Double.parseDouble(newAmount.getText().toString()),
                                System.currentTimeMillis());

                        createNewPayment(context, newPayment, activity);
                    }
                })
                .build();

        ImageButton searchCitizen = (ImageButton) dialogAddPayment.findViewById(R.id.dialogCitizenSearch);
        searchCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaysearchCitizensDialog(context, (TextView) dialogAddPayment.findViewById(R.id.dialogCitizenSelectedName));
            }
        });

        TextView citizenName = (TextView) dialogAddPayment.findViewById(R.id.dialogCitizenSelectedName);
        citizenName.setText(citizenFullName);

        dialogAddPayment.show();
    }

    private static void createNewPayment(final Context context, final Payment newPayment, final PaymentListActivity activity) {
        new AsyncTask<Payment, Void, String>() {
            protected String doInBackground(Payment... newPayments) {
                try {
                    KremesDatabase.getAppDatabase(context).paymentDao().insert(newPayments[0]);
                    return newPayment.getCitizenUsername();
                } catch (Exception e) {
                    return null;
                }

            }

            protected void onPostExecute(String username) {
                if (username != null && !username.isEmpty()) {
                    if(activity != null) {
                        activity.listAllPayments(FormatDateMonth(Calendar.getInstance()));
                    }
                        updateCitizenStatistic(context, username);
                        Toast.makeText(context, "Uplata uspješno unesena", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(context, "Doslo je do greške, Uplata nije dodana", Toast.LENGTH_LONG).show();

            }
        }.execute(newPayment);
    }
}

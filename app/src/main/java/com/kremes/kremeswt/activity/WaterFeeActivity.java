package com.kremes.kremeswt.activity;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Fee;
import com.kremes.kremeswt.utils.GeneralUtils;

import java.util.Calendar;

import static com.kremes.kremeswt.utils.GeneralUtils.FormatDateMonth;

public class WaterFeeActivity extends AppCompatActivity {

    KremesDatabase db;

    TextView tvPriceForCurrentMonth;
    TextView tvPriceForPrevMonth;

    Button btnChangeWaterFeeForCurrentMonth;

    double dialogPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_fee);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        db = KremesDatabase.getAppDatabase(WaterFeeActivity.this);

        tvPriceForCurrentMonth = findViewById(R.id.tvPriceForCurrentMonth);
        tvPriceForPrevMonth = findViewById(R.id.tvPriceForPrevMonth);
        btnChangeWaterFeeForCurrentMonth = findViewById(R.id.btnChangeWaterFeeForCurrentMonth);

        setCurrentMonthText();
        setPrevMonthText();

        btnChangeWaterFeeForCurrentMonth.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(WaterFeeActivity.this)
                        .title("Promjena Cijene")
                        .content("Promjena cijene se moze izvršiti samo za trenutni mjesec.\nNova Cijena ce se automatski prebaciti i na slijedeći mjesec.")
                        .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        .input("Nova Cijena u KM", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                dialogPrice = Double.parseDouble(input.toString());
                                if(dialogPrice > 0)
                                    updatePriceForCurrentMonth();
                            }
                        })
                        .positiveText("SPASI")
                        .show();
            }
        });
    }


    private void setCurrentMonthText() {
        new AsyncTask<Void, Void, Double>() {
            protected Double doInBackground(Void... params) {
                return db.feeDao().getByDateMonth(GeneralUtils.FormatDateMonth(System.currentTimeMillis()));
            }

            protected void onPostExecute(Double waterFeeAmount) {
                tvPriceForCurrentMonth.setText("Cijena za "
                        + GeneralUtils.getGregorianMonthName(WaterFeeActivity.this, Calendar.getInstance().get(Calendar.MONTH))
                        + " ce iznositi: "
                        + waterFeeAmount
                        + "KM");
            }
        }.execute();
    }

    private void setPrevMonthText() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        new AsyncTask<Void, Void, Double>() {
            protected Double doInBackground(Void... params) {
                return db.feeDao().getByDateMonth(GeneralUtils.FormatDateMonth(cal));
            }

            protected void onPostExecute(Double waterFeeAmount) {
                tvPriceForPrevMonth.setText("Cijena za "
                        + GeneralUtils.getGregorianMonthName(WaterFeeActivity.this, cal.get(Calendar.MONTH))
                        + " je bila: "
                        + waterFeeAmount
                        + "KM");
            }
        }.execute();
    }

    private void updatePriceForCurrentMonth() {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                if(dialogPrice < 0)
                    return null;
                Fee currentFee = db.feeDao().getFee(GeneralUtils.FormatDateMonth(Calendar.getInstance()));
                if(currentFee != null) {
                    currentFee.setPrice(dialogPrice);
                    db.feeDao().update(currentFee);
                }
                else
                    db.feeDao().insertAll(new Fee(FormatDateMonth(Calendar.getInstance()), dialogPrice));
                return null;
            }

            protected void onPostExecute(Void r) {
                setCurrentMonthText();
            }
        }.execute();
    }
}

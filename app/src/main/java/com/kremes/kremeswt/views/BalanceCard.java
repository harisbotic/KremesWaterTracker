package com.kremes.kremeswt.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;

import static com.kremes.kremeswt.utils.CitizenUtils.getCitizenFullName;

/**
 * Created by Bota
 */

public class BalanceCard extends RelativeLayout implements View.OnClickListener {
    Context context;
    KremesDatabase db;

    TextView balanceCardName;
    TextView balanceCardBalance;

    Citizen citizen;

    public BalanceCard(Context context, Citizen citizen) {
        super(context);
        this.context = context;
        this.citizen = citizen;
        this.db = KremesDatabase.getAppDatabase(context);

        init();
        setOnClickListener(this);
    }

    private void init() {
        inflate(getContext(), R.layout.element_balance_card, this);
        balanceCardName = findViewById(R.id.balanceCardName);
        balanceCardName.setText(getCitizenFullName(citizen));
        balanceCardBalance = findViewById(R.id.balanceCardBalance);
        String plusSign = (citizen.getBalance() > 0)? "+": "";
        balanceCardBalance.setText(plusSign + citizen.getBalance() + " KM");
    }

    @Override
    public void onClick(View v) {
        new MaterialDialog.Builder(context)
                .title("SMS Poruka")
                .positiveText("POSALJI SMS PORUKU")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Uri uri = Uri.parse("smsto:" + citizen.getPhoneNumber());
                        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                        it.putExtra("sms_body", "Poštovanje, " + getCitizenFullName(citizen));
                        context.startActivity(it);
                    }
                })
                .neutralText("ZATVORI")
                .show();
    }
}
package com.kremes.kremeswt.views;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;

import static com.kremes.kremeswt.utils.CitizenUtils.getCitizenFullName;

/**
 * Created by Bota
 */

public class CitizenCard extends RelativeLayout implements CardView.OnClickListener {
    Context context;
    KremesDatabase db;

    TextView citizenCardName;
    TextView citizenCardPhone;

    Citizen citizen;
    MaterialDialog dialogEditCitizen;

    public CitizenCard(Context context, Citizen citizen) {
        super(context);
        this.context = context;
        this.citizen = citizen;
        this.db = KremesDatabase.getAppDatabase(context);

        init();
        setOnClickListener(this);
    }

    private void init() {
        inflate(getContext(), R.layout.element_citizen_card, this);
        citizenCardName = findViewById(R.id.citizenCardName);
        citizenCardName.setText(getCitizenFullName(citizen));
        citizenCardPhone = findViewById(R.id.citizenCardPhone);
        citizenCardPhone.setText(citizen.getPhoneNumber());

        dialogEditCitizen = new MaterialDialog.Builder(context)
                                .title("Izmjena Korisnika")
                                .customView(R.layout.dialog_citizen_edit, false)
                                .autoDismiss(true)
                                .cancelable(true)
                                .positiveText("IZMJENI")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        EditText editPhoneNumber = (EditText) dialog.findViewById(R.id.etNewPhoneNumber);
                                        citizen.setPhoneNumber(editPhoneNumber.getText().toString());
                                        editThisCitizen();
                                    }
                                })
                                .build();
    }

    @Override
    public void onClick(View v) {
        new MaterialDialog.Builder(context)
                .title("Odaberi Akciju")
                .content("Ukoliko se Korisnik izbriše, izbrisat ce se i svi njegovi podaci!!!")
                .positiveText("NAPRAVI IZMJENU")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialogEditCitizen.show();
                    }
                })
                .neutralText("IZBRIŠI KORISNIKA")
                .neutralColor(Color.parseColor("#B33A3A"))
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showDeleteCitizenDialog();
                    }
                })
                .show();
    }

    public void adjustColors() {
//        citizenCardName.setTextColor(Color.parseColor("#FFC107"));
//        citizenCardPhone.setTextColor(Color.parseColor("#FFC107"));
    }

    private void editThisCitizen() {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                db.citizenDao().update(citizen);
                return null;
            }

            protected void onPostExecute(Void r) {
                Toast.makeText(context, "Podaci Uspješno Izmjenjeni", Toast.LENGTH_LONG).show();
                citizenCardPhone.setText(citizen.getPhoneNumber());
            }
        }.execute();
    }

    private void showDeleteCitizenDialog() {
        new MaterialDialog.Builder(context)
                .title(getCitizenFullName(citizen) + " ?")
                .content("Da li sigurno želite izbrisati Korisnika?\nUkoliko se Korisnik izbriše, izbrisat ce se i svi njegovi podaci!!!")
                .positiveText("IZBRIŠI")
                .negativeText("NE ŽELIM")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteThisCitizen();
                    }
                })
                .show();
    }

    private void deleteThisCitizen() {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                db.citizenDao().delete(citizen);
                return null;
            }

            protected void onPostExecute(Void r) {
                setVisibility(GONE);
                Toast.makeText(context, "Korisnik Izbrisan", Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

}
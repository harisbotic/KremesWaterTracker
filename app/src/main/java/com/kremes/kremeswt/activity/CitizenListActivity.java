package com.kremes.kremeswt.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;
import com.kremes.kremeswt.views.CitizenCard;

import java.util.List;

public class CitizenListActivity extends AppCompatActivity {

    LinearLayout citizenCardHolder;

    MaterialDialog dialogAddCitizen;
    KremesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_list);
        db = KremesDatabase.getAppDatabase(CitizenListActivity.this);
        listAllCitizens();

        citizenCardHolder = findViewById(R.id.citizenCardHolder);

        FloatingActionButton fab = findViewById(R.id.fabCitizenAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNewCitizenDialog();
            }
        });
    }

    private void displayNewCitizenDialog() {
        dialogAddCitizen = new MaterialDialog.Builder(CitizenListActivity.this)
                .title("Novi Korisnik")
                .customView(R.layout.dialog_citizen_add, false)
                .autoDismiss(true)
                .cancelable(false)
                .negativeText("ODUSTANI")
                .positiveText("DODAJ KORISNIKA")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        EditText newName = (EditText) dialog.findViewById(R.id.dialogCitizenNewName);
                        EditText newSurname = (EditText) dialog.findViewById(R.id.dialogCitizenNewSurname);
                        EditText newWaterMeterNumber = (EditText) dialog.findViewById(R.id.dialogCitizenNewWaterMeterNumber);
                        EditText newPhone = (EditText) dialog.findViewById(R.id.dialogCitizenNewPhone);

                        Citizen newCitizen = new Citizen(
                                newName.getText().toString(),
                                newSurname.getText().toString(),
                                newPhone.getText().toString(),
                                Long.parseLong(newWaterMeterNumber.getText().toString()),
                                0,0);

                        createNewCitizen(newCitizen);
                    }
                })
                .show();
        };

    private void listAllCitizens() {
        new AsyncTask<Void, Void, List<Citizen>>() {
            protected List<Citizen> doInBackground(Void... v) {
                return db.citizenDao().getAll();

            }

            protected void onPostExecute(List<Citizen> allCitizens) {
                for (Citizen citizen: allCitizens) {
                    citizenCardHolder.addView(new CitizenCard(CitizenListActivity.this, citizen));
                }
            }
        }.execute();
    }

    private void createNewCitizen(final Citizen newCitzen) {
        new AsyncTask<Citizen, Void, Citizen>() {
            protected Citizen doInBackground(Citizen... newCitizens) {
                if(db.citizenDao().insert(newCitizens[0]) >= 1)
                    return newCitzen;
                else
                    return null;

            }

            protected void onPostExecute(Citizen createdCitizen) {
                if(createdCitizen != null) {
                    citizenCardHolder.addView(new CitizenCard(CitizenListActivity.this, createdCitizen),0);
                    Toast.makeText(CitizenListActivity.this, "Korisnik uspješno dodan!", Toast.LENGTH_LONG).show();

                } else
                    Toast.makeText(CitizenListActivity.this, "Doslo je do greške, korisnik nije dodan", Toast.LENGTH_LONG).show();

            }
        }.execute(newCitzen);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}

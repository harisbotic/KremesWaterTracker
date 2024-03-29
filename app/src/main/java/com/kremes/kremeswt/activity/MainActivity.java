package com.kremes.kremeswt.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kremes.kremeswt.R;
import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;
import com.kremes.kremeswt.entity.Report;
import com.kremes.kremeswt.utils.BackupUtils;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static com.kremes.kremeswt.utils.BackupUtils.importBackup;
import static com.kremes.kremeswt.utils.PaymentUtils.displayNewPaymentDialog;
import static com.kremes.kremeswt.utils.PermissionUtils.checkAndRequestPermissions;
import static com.kremes.kremeswt.utils.ReportUtils.displayNewReportDialog;
import static com.kremes.kremeswt.utils.SMSUtils.readAllSMS;
import static com.kremes.kremeswt.utils.WaterFeeUtils.UpdateWaterFees;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    Button btnStatistics;
    Button btnNewReport;
    Button btnNewPayment;
    Button btnChangeWaterFee;
    Button btnBackupDb;
    Button btnImportDb;
    Button btnSendSMSToEveryone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        checkAndRequestPermissions(MainActivity.this);

        btnStatistics = findViewById(R.id.btnStatistics);
        btnStatistics.setOnClickListener(this);
        btnNewReport = findViewById(R.id.btnNewReport);
        btnNewReport.setOnClickListener(this);
        btnNewPayment = findViewById(R.id.btnNewPayment);
        btnNewPayment.setOnClickListener(this);
        btnChangeWaterFee = findViewById(R.id.btnChangeWaterFee);
        btnChangeWaterFee.setOnClickListener(this);
        btnBackupDb = findViewById(R.id.btnBackupDb);
        btnBackupDb.setOnClickListener(this);
        btnImportDb = findViewById(R.id.btnImportDb);
        btnImportDb.setOnClickListener(this);
        btnSendSMSToEveryone = findViewById(R.id.btnSendSMSToEveryone);
        btnSendSMSToEveryone.setOnClickListener(this);
//        updateAllUsers();
    }

    @Override
    protected void onDestroy() {
        KremesDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateWaterFees(MainActivity.this);
        readAllSMS(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStatistics:
                startActivity(new Intent(MainActivity.this, MenuStatisticsActivity.class));
                break;

            case R.id.btnNewReport:
                displayNewReportDialog(MainActivity.this, "", null);
                break;

            case R.id.btnNewPayment:
                displayNewPaymentDialog(MainActivity.this, "", null);
                break;

            case R.id.btnChangeWaterFee:
                startActivity(new Intent(MainActivity.this, WaterFeeActivity.class));
                break;

            case R.id.btnBackupDb:
                BackupUtils.shareBackupFile(MainActivity.this);
                break;

            case R.id.btnImportDb:
                findFileForImport();
                break;

            case R.id.btnSendSMSToEveryone:
                sendSMSToEveryone();
                break;
        }
    }

    private void findFileForImport() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(159)
                .withRootPath(Environment.getExternalStorageDirectory().getPath()) //Pre 4.4 /storage/sdcard0/ -- /storage/emulated/0/
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 159 && resultCode == RESULT_OK) {
            final String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            new MaterialDialog.Builder(MainActivity.this)
                    .title("Da li sigurno želite unijeti podatke?")
                    .content("Ako pristanete, svi trenutni podaci na telefonu ce biti obrisani, i zamjenit ce se sa odabranim!!!")
                    .positiveText("IZMJENI PODATKE")
                    .negativeText("ODUSTANI")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            try {
                                importBackup(MainActivity.this, filePath);
                            }catch (IOException e){
                                Toast.makeText(MainActivity.this, "DOSLO JE DO GREŠKE", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .show();
        }
    }

    private void sendSMSToEveryone() {
        new AsyncTask<Void, Void, List<Citizen>>() {
            protected List<Citizen> doInBackground(Void... v) {
                return KremesDatabase.getAppDatabase(MainActivity.this).citizenDao().getAll();
            }

            protected void onPostExecute(List<Citizen> allCitizens) {
                String phoneNumbers = "";
                for (Citizen citizen: allCitizens) {
                    phoneNumbers = phoneNumbers + citizen.getPhoneNumber() + ";";
                }
                phoneNumbers = phoneNumbers.substring(0, phoneNumbers.length()-1);
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumbers)));
            }
        }.execute();
    }

    //DONT UNCOMMENT THIS
//    private void updateAllUsers() {
//        new AsyncTask<Void, Void, String>() {
//            protected String doInBackground(Void... v) {
//                    List<Report> reports = KremesDatabase.getAppDatabase(MainActivity.this).reportDao().getAllForUsername("sefikkadiric");
//                    long total = 0;
//                    for (Report report : reports) {
//                        System.out.println("On ima:" + report.getWaterAmount());
//                }
//                return "";
//            }
//
//            protected void onPostExecute(String as) {
//
//            }
//        }.execute();
//    }
}

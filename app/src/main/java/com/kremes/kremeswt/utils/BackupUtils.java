package com.kremes.kremeswt.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Toast;

import com.kremes.kremeswt.BuildConfig;
import com.kremes.kremeswt.activity.MainActivity;
import com.kremes.kremeswt.database.KremesDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.kremes.kremeswt.database.KremesDatabase.DB_NAME;
import static com.kremes.kremeswt.utils.GeneralUtils.formatCurrentDate;

import androidx.core.content.FileProvider;

/**
 * Created by Bota on 10/8/2017.
 */

public class BackupUtils {

    public static void importBackup(Context context, String filepath) throws IOException  {
        InputStream in = new FileInputStream(filepath);
        try {
            OutputStream out = new FileOutputStream(getDatabaseFile(context));
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public static void shareBackupFile(final Context context) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Kremes Backup " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "body text");
        Uri uri = getDatabaseFileUri(context);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(emailIntent, "Izaberi Email providera"));
    }

    public static String getDatabaseFile(Context context) {
        File data = Environment.getDataDirectory();
        String currentDBPath = "/data/" + context.getPackageName() + "/databases/" + DB_NAME;
        return new File(data, currentDBPath).getAbsolutePath();
    }

    private static Uri getDatabaseFileUri(Context context){
        File data = Environment.getDataDirectory();
        String currentDBPath = "/data/" + context.getPackageName() + "/databases/" + DB_NAME;
        File currentDB = new File(data, currentDBPath);
        return FileProvider.getUriForFile(
                context,
                "com.kremes.kremeswt" + ".provider",
                currentDB);
    }
}

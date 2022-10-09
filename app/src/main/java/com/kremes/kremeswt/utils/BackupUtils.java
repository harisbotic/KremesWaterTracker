package com.kremes.kremeswt.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.kremes.kremeswt.database.KremesDatabase.DB_NAME;
import static com.kremes.kremeswt.utils.GeneralUtils.formatCurrentDate;

/**
 * Created by Bota on 10/8/2017.
 */

public class BackupUtils {

    public static void importBackup(Context context, String filepath) throws IOException  {
        InputStream in = new FileInputStream(filepath);
        try {
            OutputStream out = new FileOutputStream(getDatabaseFilePath(context));
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
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri databaseUri = Uri.parse(getDatabaseFilePath(context));
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, databaseUri);
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private static String getDatabaseFilePath(Context context){
        File data = Environment.getDataDirectory();
        String currentDBPath = "/data/" + context.getPackageName() + "/databases/" + DB_NAME;
        File currentDB = new File(data, currentDBPath);
        return currentDB.getAbsolutePath();
    }
}

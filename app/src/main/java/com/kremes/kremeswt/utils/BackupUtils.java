package com.kremes.kremeswt.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import static android.R.attr.src;
import static com.kremes.kremeswt.database.KremesDatabase.DB_NAME;
import static com.kremes.kremeswt.database.KremesDatabase.getAppDatabase;

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

    public static void sendBackupToEmail(final Context context) {
        BackgroundMail.newBuilder(context)
                .withUsername("androvana@gmail.com")
                .withPassword("just2guys")
                .withMailto("androvana@gmail.com")
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("this is the subject")
                .withBody("this is the body")
                .withAttachments(getDatabaseFilePath(context))
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                       Toast.makeText(context, "mislim da sam poslo'", Toast.LENGTH_LONG).show();
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(context, "fejlo' sam", Toast.LENGTH_LONG).show();
                    }
                })
                .send();
    }
    private static String getDatabaseFilePath(Context context){
        File data = Environment.getDataDirectory();
        String currentDBPath = "/data/" + context.getPackageName() + "/databases/" + DB_NAME;
        File currentDB = new File(data, currentDBPath);
        return currentDB.getAbsolutePath();
    }
}

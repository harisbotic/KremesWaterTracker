package com.kremes.kremeswt.utils;

import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Citizen;

/**
 * Created by Bota
 */

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final KremesDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final KremesDatabase db) {
        populateWithTestData(db);
    }

    private static Citizen addCitizen(final KremesDatabase db, Citizen user) {
        db.citizenDao().insertAll(user);
        return user;
    }

    private static void populateWithTestData(KremesDatabase db) {
//        Citizen user = new Citizen();
//        user.setFirstName("Ajay");
//        user.setLastName("Saini");
//        addCitizen(db, user);
//
//        List<Citizen> userList = db.citizenDao().getAll();
//        Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final KremesDatabase mDb;

        PopulateDbAsync(KremesDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}

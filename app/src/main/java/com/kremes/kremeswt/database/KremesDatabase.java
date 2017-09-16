package com.kremes.kremeswt.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kremes.kremeswt.dao.CitizenDao;
import com.kremes.kremeswt.entity.Citizen;

/**
 * Created by Bota
 */


@Database(entities = {Citizen.class}, version = 1)
public abstract class KremesDatabase extends RoomDatabase {

    private static KremesDatabase INSTANCE;

    public abstract CitizenDao citizenDao();

    public static KremesDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), KremesDatabase.class, "kremes-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

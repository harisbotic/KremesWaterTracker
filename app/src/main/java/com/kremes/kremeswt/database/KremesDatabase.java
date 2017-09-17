package com.kremes.kremeswt.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kremes.kremeswt.dao.CitizenDao;
import com.kremes.kremeswt.dao.FeeDao;
import com.kremes.kremeswt.dao.PaymentDao;
import com.kremes.kremeswt.dao.PaymentRequestDao;
import com.kremes.kremeswt.dao.ReportDao;
import com.kremes.kremeswt.entity.Citizen;
import com.kremes.kremeswt.entity.Fee;
import com.kremes.kremeswt.entity.Payment;
import com.kremes.kremeswt.entity.PaymentRequest;
import com.kremes.kremeswt.entity.Report;

/**
 * Created by Bota
 */


@Database(entities = {Citizen.class, Report.class, Fee.class, Payment.class, PaymentRequest.class}, version = 1)
public abstract class KremesDatabase extends RoomDatabase {
    public static final String DB_NAME = "kremes-database";
    private static KremesDatabase INSTANCE;

    public abstract CitizenDao citizenDao();
    public abstract ReportDao reportDao();
    public abstract FeeDao feeDao();
    public abstract PaymentDao paymentDao();
    public abstract PaymentRequestDao paymentRequestDao();

    public static KremesDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), KremesDatabase.class, DB_NAME)
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

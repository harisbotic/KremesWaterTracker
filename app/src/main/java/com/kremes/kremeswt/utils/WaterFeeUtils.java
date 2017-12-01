package com.kremes.kremeswt.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Fee;

import java.util.ArrayList;
import java.util.List;

import static com.kremes.kremeswt.utils.GeneralUtils.FormatDateMonth;

/**
 * Created by Bota on 10/8/2017.
 */

public class WaterFeeUtils {

    public static Fee getFeeByDateMonth(List<Fee> allFees, String dateMonth) {
        for (Fee fee: allFees) {
            if(fee.getDateMonth().equals(dateMonth))
                return fee;
        }
        return null;
    }

    public static void UpdateWaterFees(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... voids) {
                try {
                    if(KremesDatabase.getAppDatabase(context).feeDao().getFeeCount() <= 0) {
                        Fee newFee = new Fee(FormatDateMonth(-1), 2.5);
                        KremesDatabase.getAppDatabase(context).feeDao().insertAll(newFee);
                        return null;
                    }

                    ArrayList<String> feesNeededToFill = new ArrayList<>();
                    Fee lastKnownFee = null;
                    for (int i = 0; i > -100; i--) {
                        Fee fee = KremesDatabase.getAppDatabase(context).feeDao().getFee(FormatDateMonth(i));
                        if(fee != null) {
                            lastKnownFee = fee;
                            break;
                        }
                        else
                            feesNeededToFill.add(FormatDateMonth(i));
                    }

                    for (String dateMonth: feesNeededToFill) {
                        Fee newFee = new Fee(dateMonth, lastKnownFee.getPrice());
                        KremesDatabase.getAppDatabase(context).feeDao().insertAll(newFee);
                    }
                    return null;
                } catch (Exception e) {
                    return null;
                }
            }

            protected void onPostExecute() {
            }
        }.execute();
    }

}
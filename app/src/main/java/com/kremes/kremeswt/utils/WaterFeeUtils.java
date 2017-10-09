package com.kremes.kremeswt.utils;

import android.content.Context;

import com.kremes.kremeswt.database.KremesDatabase;
import com.kremes.kremeswt.entity.Fee;

import java.util.ArrayList;

/**
 * Created by Bota on 10/8/2017.
 */

public class WaterFeeUtils {

    public static void UpdateWaterFees(Context context) {
        if(KremesDatabase.getAppDatabase(context).feeDao().getFeeCount() <= 0)
            return;

        ArrayList<String> feesNeededToFill = new ArrayList<>();
        Fee lastKnownFee = null;
        for (int i = 0; i > -100; i--) {
            Fee fee = KremesDatabase.getAppDatabase(context).feeDao().getFee(GeneralUtils.FormatDateMonth(i));
            if(fee != null) {
                lastKnownFee = fee;
                break;
            }
            else
                feesNeededToFill.add(GeneralUtils.FormatDateMonth(i));
        }

        for (String dateMonth: feesNeededToFill) {
            Fee newFee = new Fee(dateMonth, lastKnownFee.getPrice());
            KremesDatabase.getAppDatabase(context).feeDao().insertAll(newFee);
        }
    }

}
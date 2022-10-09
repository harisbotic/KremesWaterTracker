package com.kremes.kremeswt.model;

import androidx.room.ColumnInfo;

import com.kremes.kremeswt.entity.Citizen;

/**
 * Created by Bota
 */

public class CitizenWithReport extends Citizen {

    @ColumnInfo(name = "waterAmountLastMonth")
    private long waterAmountLastMonth;

    @ColumnInfo(name = "waterAmountLastTwoMonth")
    private long waterAmountLastTwoMonth;

    public CitizenWithReport() {
    }

    public long getWaterAmountLastMonth() {
        return waterAmountLastMonth;
    }

    public void setWaterAmountLastMonth(long waterAmountLastMonth) {
        this.waterAmountLastMonth = waterAmountLastMonth;
    }

    public long getWaterAmountLastTwoMonth() {
        return waterAmountLastTwoMonth;
    }

    public void setWaterAmountLastTwoMonth(long waterAmountLastTwoMonth) {
        this.waterAmountLastTwoMonth = waterAmountLastTwoMonth;
    }
}

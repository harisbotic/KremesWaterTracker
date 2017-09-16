package com.kremes.kremeswt.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Bota
 */

@Entity(tableName = "report",
        foreignKeys = {
                @ForeignKey(
                        entity = Citizen.class,
                        parentColumns = "username",
                        childColumns = "citizen_username"
                )},
        indices = { @Index(value = "citizen_username")}
)
public class Report {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "citizen_username")
    private String citizenUsername;

    @ColumnInfo(name = "date_month")
    private long dateMonth;

    @ColumnInfo(name = "water_amount")
    private long waterAmount;

    @ColumnInfo(name = "date_received")
    private String dateReceived;

    public Report() {
    }

    public Report(String citizenUsername, int forMonth, long waterAmount, String dateReceived) {
        this.citizenUsername = citizenUsername;
        this.dateMonth = forMonth;
        this.waterAmount = waterAmount;
        this.dateReceived = dateReceived;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCitizenUsername() {
        return citizenUsername;
    }

    public void setCitizenUsername(String citizenUsername) {
        this.citizenUsername = citizenUsername;
    }

    public long getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(long dateMonth) {
        this.dateMonth = dateMonth;
    }

    public long getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(long waterAmount) {
        this.waterAmount = waterAmount;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }
}

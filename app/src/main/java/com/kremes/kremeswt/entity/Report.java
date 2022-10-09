package com.kremes.kremeswt.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Created by Bota
 */

@Entity(tableName = "report",
        foreignKeys = {
                @ForeignKey(
                        entity = Citizen.class,
                        parentColumns = "username",
                        childColumns = "citizen_username",
                        onDelete = CASCADE
                )},
        indices = { @Index(value = "citizen_username"),
                    @Index(value = {"citizen_username", "date_month"}, unique = true)}
)
public class Report {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "citizen_username")
    private String citizenUsername;

    @ColumnInfo(name = "date_month")
    private String dateMonth;

    @ColumnInfo(name = "water_amount")
    private long waterAmount;

    @ColumnInfo(name = "date_received")
    private long dateReceived;

    public Report() {
    }

    @Ignore
    public Report(String citizenUsername, String dateMonth, long waterAmount) {
        this.citizenUsername = citizenUsername;
        this.dateMonth = dateMonth;
        this.waterAmount = waterAmount;
        this.dateReceived = System.currentTimeMillis();
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

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public long getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(long waterAmount) {
        this.waterAmount = waterAmount;
    }

    public long getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(long dateReceived) {
        this.dateReceived = dateReceived;
    }
}

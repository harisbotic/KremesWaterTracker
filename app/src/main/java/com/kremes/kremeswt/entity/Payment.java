package com.kremes.kremeswt.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Bota
 */

@Entity(tableName = "payment",
        foreignKeys = {
                @ForeignKey(
                        entity = Citizen.class,
                        parentColumns = "username",
                        childColumns = "citizen_username"
                )},
        indices = { @Index(value = "citizen_username")}
)
public class Payment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "citizen_username")
    private String citizenUsername;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "date_month")
    private String dateMonth;

    @ColumnInfo(name = "date_paid")
    private long datePaid;

    public Payment() {
    }

    @Ignore
    public Payment(String citizenUsername, String dateMonth, double amount, long date_paid) {
        this.citizenUsername = citizenUsername;
        this.dateMonth = dateMonth;
        this.amount = amount;
        this.datePaid = date_paid;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(long datePaid) {
        this.datePaid = datePaid;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }
}

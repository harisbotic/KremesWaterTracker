package com.kremes.kremeswt.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Bota
 */

@Entity(tableName = "payment_request",
        foreignKeys = {
                @ForeignKey(
                        entity = Citizen.class,
                        parentColumns = "username",
                        childColumns = "citizen_username"
                )},
        indices = { @Index(value = "citizen_username")}
)
public class PaymentRequest {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "citizen_username")
    private String citizenUsername;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "date_requested")
    private String dateRequested;

    @ColumnInfo(name = "date_approved")
    private String dateApproved;

    public PaymentRequest(String citizenUsername, double amount, String dateRequested) {
        this.citizenUsername = citizenUsername;
        this.amount = amount;
        this.dateRequested = dateRequested;
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

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public String getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(String dateApproved) {
        this.dateApproved = dateApproved;
    }
}

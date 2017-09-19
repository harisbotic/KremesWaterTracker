package com.kremes.kremeswt.model;

import android.arch.persistence.room.ColumnInfo;

import com.kremes.kremeswt.entity.Citizen;

/**
 * Created by Bota
 */

public class CitizenWithPayment extends Citizen {
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "date_paid")
    private long datePaid;

    @ColumnInfo(name = "date_month")
    private String dateMonth;

    public CitizenWithPayment() {
    }

    public double getAmount() {
        return amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

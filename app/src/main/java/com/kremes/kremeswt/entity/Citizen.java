package com.kremes.kremeswt.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static com.kremes.kremeswt.utils.CitizenUtils.formatCitizenPhoneNumer;
import static com.kremes.kremeswt.utils.CitizenUtils.formatUsername;
import static com.kremes.kremeswt.utils.GeneralUtils.formatCapitalFirstLetter;

/**
 * Created by Bota
 */

@Entity(tableName = "citizen")
public class Citizen {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "date_joined")
    private String dateJoined;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "water_meter_number")
    @Nullable
    private long waterMeterNumber = 0;

    @ColumnInfo(name = "water_spent")
    private double waterSpent;

    @ColumnInfo(name = "balance")
    private double balance;

    public Citizen() {
    }

    @Ignore
    public Citizen(String firstName, String lastName, String phoneNumber, long waterMeterNumber, double waterSpent, double balance) {
        this.username = formatUsername(firstName, lastName);
        this.firstName = formatCapitalFirstLetter(firstName);
        this.lastName = formatCapitalFirstLetter(lastName);
        this.phoneNumber = formatCitizenPhoneNumer(phoneNumber);
        this.waterMeterNumber = waterMeterNumber;
        this.waterSpent = waterSpent;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = formatCitizenPhoneNumer(phoneNumber);
    }

    public long getWaterMeterNumber() {
        return waterMeterNumber;
    }

    public void setWaterMeterNumber(long waterMeterNumber) {
        this.waterMeterNumber = waterMeterNumber;
    }

    public double getWaterSpent() {
        return waterSpent;
    }

    public void setWaterSpent(double waterSpent) {
        this.waterSpent = waterSpent;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
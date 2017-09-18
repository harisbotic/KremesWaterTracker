package com.kremes.kremeswt.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static com.kremes.kremeswt.utils.CitizenUtils.formatCitizenPhoneNumer;
import static com.kremes.kremeswt.utils.CitizenUtils.formatUsername;
import static com.kremes.kremeswt.utils.GeneralUtils.formatCapitalFirstLetter;

/**
 * Created by Bota
 */

@Entity(tableName = "citizen")
public class Citizen {

    @PrimaryKey
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

    @ColumnInfo(name = "water_spent")
    private long waterSpent;

    @ColumnInfo(name = "balance")
    private long balance;

    @Ignore
    public Report reportForLastMonth;

    public Citizen() {
    }

    @Ignore
    public Citizen(String firstName, String lastName, String phoneNumber, long waterSpent, long balance) {
        this.username = formatUsername(firstName, lastName);
        this.firstName = formatCapitalFirstLetter(firstName);
        this.lastName = formatCapitalFirstLetter(lastName);
        this.phoneNumber = formatCitizenPhoneNumer(phoneNumber);
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

    public long getWaterSpent() {
        return waterSpent;
    }

    public void setWaterSpent(long waterSpent) {
        this.waterSpent = waterSpent;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
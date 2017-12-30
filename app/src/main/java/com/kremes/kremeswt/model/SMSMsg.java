package com.kremes.kremeswt.model;

/**
 * Created by Bota
 */

public class SMSMsg {

    private int id;
    private String address;
    private String message;
    private String date;

    public SMSMsg() {
    }

    public SMSMsg(String address, String msg, String date) {
        this.address = address;
        this.message = msg;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

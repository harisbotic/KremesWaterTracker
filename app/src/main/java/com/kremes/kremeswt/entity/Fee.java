package com.kremes.kremeswt.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Bota
 */

@Entity(tableName = "fee",
        indices = { @Index(value = "date_month", unique = true)}
)
public class Fee {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "date_month")
    private String dateMonth;

    public Fee() {
    }

    public Fee(double price, String forMonth) {
        this.price = price;
        this.dateMonth = forMonth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }
}

package com.kremes.kremeswt.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by Bota
 */

@Entity(tableName = "fee",
        indices = { @Index(value = "date_month", unique = true)}
)
public class Fee {
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "date_month")
    private String dateMonth;

    @ColumnInfo(name = "price")
    private double price;

    public Fee(String dateMonth, double price) {
        this.dateMonth = dateMonth;
        this.price = price;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

package com.kremes.kremeswt.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kremes.kremeswt.entity.Fee;

import java.util.List;

/**
 * Created by Bota
 */

@Dao
public interface FeeDao {
    @Query("SELECT * FROM fee WHERE date_month = :dateMonth LIMIT 1")
    Fee getFee(String dateMonth);

    @Query("SELECT * FROM fee")
    List<Fee> getAll();

    @Query("SELECT fee.price FROM fee WHERE date_month = :dateMonth LIMIT 1")
    double getByDateMonth(String dateMonth);

    @Query("SELECT COUNT(*) FROM fee")
    double getFeeCount();

    @Update
    void update(Fee fee);

    @Insert
    void insertAll(Fee... fees);
}
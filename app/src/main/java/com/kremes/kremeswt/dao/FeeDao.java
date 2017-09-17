package com.kremes.kremeswt.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Update
    void update(Fee fee);

    @Insert
    void insertAll(Fee... fees);
}
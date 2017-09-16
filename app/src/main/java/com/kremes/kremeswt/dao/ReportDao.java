package com.kremes.kremeswt.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kremes.kremeswt.entity.Report;

import java.util.List;

/**
 * Created by Bota
 */

@Dao
public interface ReportDao {
    @Query("SELECT * FROM report")
    List<Report> getAll();

    @Query("SELECT * FROM report WHERE id = :dateMonth")
    List<Report> getAllForDateMonth(String dateMonth);

    @Insert
    void insertAll(Report... reports);

    @Update
    void updateReport(Report report);

    @Delete
    void delete(Report report);
}
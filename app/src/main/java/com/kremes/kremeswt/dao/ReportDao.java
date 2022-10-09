package com.kremes.kremeswt.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM report WHERE citizen_username = :username")
    List<Report> getAllForUsername(String username);

    @Query("SELECT * FROM report WHERE citizen_username = :username AND date_month = :dateMonth")
    Report findByUsernameAndDateMonth(String username, String dateMonth);

    @Insert
    long insert(Report report);

    @Update
    void update(Report report);

    @Insert
    void insertAll(Report... reports);

    @Delete
    int delete(Report report);
}
package com.kremes.kremeswt.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kremes.kremeswt.entity.Citizen;

import java.util.List;

/**
 * Created by Bota
 */

@Dao
public interface CitizenDao {
    @Query("SELECT * FROM user")
    List<Citizen> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:citizenIds)")
    List<Citizen> loadAllByIds(int[] citizenIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Citizen findByName(String first, String last);

    @Insert
    void insertAll(Citizen... cizitens);

    @Delete
    void delete(Citizen citizen);
}
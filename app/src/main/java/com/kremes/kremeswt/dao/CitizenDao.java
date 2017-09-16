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
    @Query("SELECT * FROM citizen")
    List<Citizen> getAll();

    @Query("SELECT * FROM citizen WHERE username IN (:citizenUsernames)")
    List<Citizen> loadAllByIds(int[] citizenUsernames);

    @Query("SELECT * FROM citizen WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Citizen findByName(String first, String last);

    @Insert
    void insertAll(Citizen... cizitens);

    @Delete
    void delete(Citizen citizen);
}
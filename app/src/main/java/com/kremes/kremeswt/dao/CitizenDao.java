package com.kremes.kremeswt.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kremes.kremeswt.entity.Citizen;
import com.kremes.kremeswt.model.CitizenWithPayment;
import com.kremes.kremeswt.model.CitizenWithReport;

import java.util.List;

/**
 * Created by Bota
 */

@Dao
public interface CitizenDao {
    @Query("SELECT * FROM citizen ORDER BY first_name ASC")
    List<Citizen> getAll();

    @Query("SELECT DISTINCT c.username, c.first_name, c.last_name, c.phone_number, r1.water_amount AS waterAmountLastMonth, r2.water_amount AS waterAmountLastTwoMonth " +
            "FROM citizen c " +
            "LEFT JOIN report r1 ON r1.date_month = :lastDateMonth AND r1.citizen_username = c.username " +
            "LEFT JOIN report r2 ON r2.date_month = :lastTwoDateMonth AND r2.citizen_username = c.username " +
            "ORDER BY c.first_name ASC")
    List<CitizenWithReport> getAllWithReports(String lastDateMonth, String lastTwoDateMonth);

    @Query("SELECT c.username, c.first_name, c.last_name, c.phone_number, p.id, p.amount, p.date_paid, p.date_month " +
            "FROM citizen c " +
            "JOIN payment p ON p.date_month = :dateMonth AND p.citizen_username = c.username " +
            "ORDER BY p.date_paid DESC")
    List<CitizenWithPayment> getAllWithPayment(String dateMonth);

    @Query("SELECT * FROM citizen WHERE username IN (:citizenUsernames)")
    List<Citizen> loadAllByIds(int[] citizenUsernames);

    @Query("SELECT * FROM citizen WHERE first_name = :first AND "
            + "last_name = :last LIMIT 1")
    Citizen getByName(String first, String last);

    @Query("SELECT * FROM citizen WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Citizen findByName(String first, String last);

    @Update
    void update(Citizen citizen);

    @Insert
    long insert(Citizen citizen);

    @Insert
    void insertAll(Citizen... cizitens);

    @Delete
    void delete(Citizen citizen);
}
package com.kremes.kremeswt.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kremes.kremeswt.entity.Payment;

import java.util.List;

/**
 * Created by Bota
 */

@Dao
public interface PaymentDao {
    @Query("SELECT * FROM payment")
    List<Payment> getAll();

    @Query("SELECT * FROM payment WHERE citizen_username = :citizenUsername LIMIT 1")
    Payment findByUsername(String citizenUsername);

    @Insert
    void insertAll(Payment... payments);

    @Delete
    void delete(Payment payment);
}
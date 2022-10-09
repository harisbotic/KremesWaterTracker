package com.kremes.kremeswt.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.kremes.kremeswt.entity.Payment;

import java.util.List;

/**
 * Created by Bota
 */

@Dao
public interface PaymentDao {
    @Query("SELECT * FROM payment WHERE id = :paymentId")
    Payment getById(long paymentId);

    @Query("SELECT * FROM payment")
    List<Payment> getAll();

    @Query("SELECT * FROM payment WHERE citizen_username = :citizenUsername")
    List<Payment> getAllForUsername(String citizenUsername);

    @Insert
    long insert(Payment payments);

    @Insert
    long[] insertAll(Payment... payments);

    @Delete
    int delete(Payment payment);
}
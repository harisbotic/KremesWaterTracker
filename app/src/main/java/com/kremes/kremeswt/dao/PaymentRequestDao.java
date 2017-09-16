package com.kremes.kremeswt.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kremes.kremeswt.entity.PaymentRequest;

import java.util.List;

/**
 * Created by Bota
 */

@Dao
public interface PaymentRequestDao {
    @Query("SELECT * FROM payment_request")
    List<PaymentRequest> getAll();

    @Query("SELECT * FROM payment_request WHERE citizen_username = :citizenUsername LIMIT 1")
    PaymentRequest findByUsername(String citizenUsername);

    @Insert
    void insertAll(PaymentRequest... paymentRequests);

    @Delete
    void delete(PaymentRequest paymentRequest);
}
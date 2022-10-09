package com.kremes.kremeswt.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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
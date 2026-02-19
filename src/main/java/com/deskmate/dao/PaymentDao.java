package com.deskmate.dao;

import java.sql.Connection;
import java.util.Optional;

import com.deskmate.model.Payment;

public interface PaymentDao {
    long insertPayment(Connection conn, Payment payment);
    Optional<Payment> findByBookingId(long bookingId);
}

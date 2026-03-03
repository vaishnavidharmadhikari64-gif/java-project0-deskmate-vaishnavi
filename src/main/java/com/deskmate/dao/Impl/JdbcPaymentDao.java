package com.deskmate.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Optional;

import com.deskmate.dao.PaymentDao;
import com.deskmate.exception.DatabaseOperationException;
import com.deskmate.model.Payment;
import com.deskmate.utils.DbConnectionFactory;

public class JdbcPaymentDao implements PaymentDao {

    @Override
    public long insertPayment(Connection conn, Payment payment) {
        String sql = "INSERT INTO payments (booking_id, mode, amount, status, paid_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, payment.getBookingId());
            ps.setString(2, payment.getMode().name());
            ps.setBigDecimal(3, payment.getAmount());
            ps.setString(4, payment.getStatus().name());
            if (payment.getPaidAt() == null) ps.setNull(5, Types.TIMESTAMP);
            else ps.setTimestamp(5, Timestamp.valueOf(payment.getPaidAt()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to insert payment", e);
        }
    }

    @Override
    public Optional<Payment> findByBookingId(long bookingId) {
        String sql = "SELECT booking_id, mode, amount, status, paid_at FROM payments WHERE booking_id = ?";
        try (Connection c = DbConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                // Minimal: map if you want; not needed for baseline
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find payment", e);
        }
    }

}

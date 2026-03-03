package com.deskmate.dao.Impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import com.deskmate.constants.BookingStatus;
import com.deskmate.dao.BookingDao;
import com.deskmate.exception.DatabaseOperationException;
import com.deskmate.model.Booking;
import com.deskmate.utils.DbConnectionFactory;

public class JdbcBookingDao implements BookingDao {

    @Override
    public long insertBooking(Connection conn, long deskId, String phone, LocalDateTime start, LocalDateTime end,
                              BigDecimal total, BookingStatus status) {
        String sql = "INSERT INTO bookings (desk_id, customer_phone, slot_start, slot_end, total_amount, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, deskId);
            ps.setString(2, phone);
            ps.setTimestamp(3, Timestamp.valueOf(start));
            ps.setTimestamp(4, Timestamp.valueOf(end));
            ps.setBigDecimal(5, total);
            ps.setString(6, status.name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to insert booking", e);
        }
    }

    @Override
    public void updateStatus(Connection conn, long bookingId, BookingStatus status) {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setLong(2, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update booking status", e);
        }
    }

    @Override
    public Optional<Booking> findById(long bookingId) {
        String sql = "SELECT booking_id, desk_id, customer_phone, slot_start, slot_end, total_amount, status " +
                     "FROM bookings WHERE booking_id = ?";
        try (Connection c = DbConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new Booking(
                        rs.getLong("booking_id"),
                        rs.getLong("desk_id"),
                        rs.getString("customer_phone"),
                        rs.getTimestamp("slot_start").toLocalDateTime(),
                        rs.getTimestamp("slot_end").toLocalDateTime(),
                        rs.getBigDecimal("total_amount"),
                        BookingStatus.valueOf(rs.getString("status"))
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find booking", e);
        }
    }



}


package com.deskmate.dao.Impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.deskmate.dao.ReportDao;
import com.deskmate.exception.DatabaseOperationException;
import com.deskmate.model.report.DailyRevenueRow;
import com.deskmate.model.report.DeskUtilizationRow;
import com.deskmate.utils.DbConnectionFactory;

public class JdbcReportDao implements ReportDao {

	@Override
	public DailyRevenueRow dailyRevenue(LocalDate date) {
		String sql = "SELECT COUNT(*) AS paid_count, COALESCE(SUM(total_amount), 0) AS revenue " + "FROM bookings "
				+ "WHERE status = 'PAID' AND DATE(created_at) = ?";
		try (Connection c = DbConnectionFactory.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setDate(1, Date.valueOf(date));
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				return new DailyRevenueRow(rs.getLong("paid_count"), rs.getBigDecimal("revenue"));
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Failed daily revenue report", e);
		}
	}

	@Override
	public List<DeskUtilizationRow> deskUtilization(LocalDate date) {
		String sql = "SELECT d.desk_code, COUNT(b.booking_id) AS paid_bookings " + "FROM desks d "
				+ "LEFT JOIN bookings b ON b.desk_id = d.desk_id AND b.status = 'PAID' AND DATE(b.created_at) = ? "
				+ "GROUP BY d.desk_code " + "ORDER BY d.desk_code";
		try (Connection c = DbConnectionFactory.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setDate(1, Date.valueOf(date));
			try (ResultSet rs = ps.executeQuery()) {
				List<DeskUtilizationRow> out = new ArrayList<>();
				while (rs.next()) {
					out.add(new DeskUtilizationRow(rs.getString("desk_code"), rs.getLong("paid_bookings")));
				}
				return out;
			}
		} catch (SQLException e) {
			throw new DatabaseOperationException("Failed desk utilization report", e);
		}
	}
}

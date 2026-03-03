package com.deskmate.model.report;

import java.math.BigDecimal;

public class DailyRevenueRow {
	private final long paidBookings;
	private final BigDecimal totalRevenue;

	public DailyRevenueRow(long paidBookings, BigDecimal totalRevenue) {
		this.paidBookings = paidBookings;
		this.totalRevenue = totalRevenue;
	}

	public long getPaidBookings() {
		return paidBookings;
	}

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}
}

package com.deskmate.dao;

import java.time.LocalDate;
import java.util.List;

import com.deskmate.model.report.DailyRevenueRow;
import com.deskmate.model.report.DeskUtilizationRow;

public interface ReportDao {
	DailyRevenueRow dailyRevenue(LocalDate date);

	List<DeskUtilizationRow> deskUtilization(LocalDate date);
}

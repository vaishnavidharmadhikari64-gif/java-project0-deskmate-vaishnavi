package com.deskmate.services;

import java.time.LocalDate;
import java.util.List;

import com.deskmate.dao.ReportDao;
import com.deskmate.model.report.DailyRevenueRow;
import com.deskmate.model.report.DeskUtilizationRow;

public class ReportService {
	private final ReportDao reportDao;

	public ReportService(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public DailyRevenueRow dailyRevenue(LocalDate date) {
		return reportDao.dailyRevenue(date);
	}

	public List<DeskUtilizationRow> deskUtilization(LocalDate date) {
		return reportDao.deskUtilization(date);
	}
}

package com.deskmate.config;
	

import com.deskmate.controller.BookingController;
import com.deskmate.controller.DeskController;
import com.deskmate.controller.ReportController;
import com.deskmate.dao.BookingDao;
import com.deskmate.dao.DeskDao;
import com.deskmate.dao.PaymentDao;
import com.deskmate.dao.ReportDao;
import com.deskmate.dao.Impl.JdbcBookingDao;
import com.deskmate.dao.Impl.JdbcDeskDao;
import com.deskmate.dao.Impl.JdbcPaymentDao;
import com.deskmate.dao.Impl.JdbcReportDao;
import com.deskmate.services.BookingService;
import com.deskmate.services.DeskService;
import com.deskmate.services.ReportService;

public class AppConfig {

	    public DeskController deskController() {
	        DeskDao deskDao = new JdbcDeskDao();
	        DeskService deskService = new DeskService(deskDao);
	        return new DeskController(deskService);
	    }
	    public BookingController bookingController() {
	        DeskDao deskDao = new JdbcDeskDao();
	        BookingDao bookingDao = new JdbcBookingDao();
	        PaymentDao paymentDao = new JdbcPaymentDao();
	        BookingService bookingService = new BookingService(deskDao, bookingDao, paymentDao);
	        return new BookingController(bookingService);
	    }

	    public ReportController reportController() {
	        ReportDao reportDao = new JdbcReportDao();
	        ReportService reportService = new ReportService(reportDao);
	        return new ReportController(reportService);
	    }





}

package com.deskmate.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.deskmate.constants.PaymentMode;
import com.deskmate.constants.PaymentStatus;

	public class Payment {
	    private long paymentId;
	    private long bookingId;
	    private PaymentMode mode;
	    private BigDecimal amount;
	    private PaymentStatus status;
	    private LocalDateTime paidAt;

	    public Payment(long paymentId, long bookingId, PaymentMode mode, BigDecimal amount, PaymentStatus status, LocalDateTime paidAt) {
	        this.paymentId = paymentId;
	        this.bookingId = bookingId;
	        this.mode = mode;
	        this.amount = amount;
	        this.status = status;
	        this.paidAt = paidAt;
	    }

	    public long getBookingId() { return bookingId; }
	    public PaymentMode getMode() { return mode; }
	    public BigDecimal getAmount() { return amount; }
	    public PaymentStatus getStatus() { return status; }
	    public LocalDateTime getPaidAt() { return paidAt; }


	}


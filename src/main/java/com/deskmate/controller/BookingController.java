package com.deskmate.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.deskmate.constants.PaymentMode;
import com.deskmate.services.BookingService;
import com.deskmate.utils.DateUtil;
import com.deskmate.utils.InputUtil;
import com.deskmate.utils.MoneyUtil;

public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void menu() {
        while (true) {
            System.out.println("\n--- Bookings ---");
            System.out.println("1. Create booking + payment (transaction)");
            System.out.println("0. Back");

            int c = InputUtil.readInt("Choose: ");
            switch (c) {
                case 1 : createBooking();
                case 0 : { return; }
                default : System.out.println("Invalid option.");
            }
        }
    }

    private void createBooking() {
        String deskCode = InputUtil.readString("deskCode: ");
        String phone = InputUtil.readString("customerPhone (10 digits): ");
        LocalDateTime start = DateUtil.parseDateTime(InputUtil.readString("slotStart (yyyy-MM-dd HH:mm): "));
        LocalDateTime end = DateUtil.parseDateTime(InputUtil.readString("slotEnd   (yyyy-MM-dd HH:mm): "));
         BigDecimal total = MoneyUtil.parse(InputUtil.readString("totalAmount: "));

        System.out.println("Payment mode: 1) CASH  2) CARD  3) UPI");
        int pm = InputUtil.readInt("Choose: ");
        PaymentMode mode = null;

        switch (pm) {
            case 1:
                mode = PaymentMode.CASH;
                break;
            case 2:
                mode = PaymentMode.CARD;
                break;
            case 3:
                mode = PaymentMode.UPI;
                break;
            default:
                throw new IllegalArgumentException("Invalid payment mode");
        }


         BigDecimal paid = MoneyUtil.parse(InputUtil.readString("paidAmount (must match total): "));
        long bookingId = bookingService.createBookingWithPayment(deskCode, phone, start, end, total, mode, paid);
        System.out.println("Booking created and PAID. bookingId=" + bookingId);
    }

}

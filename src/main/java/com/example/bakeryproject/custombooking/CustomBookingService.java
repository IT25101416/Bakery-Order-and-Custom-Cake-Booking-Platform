package com.example.bakeryproject.custombooking;

import com.example.bakeryproject.common.FileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomBookingService {

    private final String FILE_PATH = "src/main/resources/data/custom_bookings.txt";

    public void addBooking(CustomBooking booking) {
        if (booking.getBookingId() == null || booking.getBookingId().trim().isEmpty()) {
            booking.setBookingId(generateBookingId());
        }

        if (booking.getStatus() == null || booking.getStatus().isEmpty()) {
            booking.setStatus("Pending");
        }

        FileUtil.saveLine(FILE_PATH, booking.toFileString());
    }

    public List<CustomBooking> getAllBookings() {
        normalizeMissingBookingIds();

        List<String> lines = FileUtil.readLines(FILE_PATH);
        List<CustomBooking> bookings = new ArrayList<>();

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                bookings.add(CustomBooking.fromFileString(line));
            }
        }

        return bookings;
    }

    public List<CustomBooking> getBookingsByCustomerName(String customerName) {
        List<CustomBooking> customerBookings = new ArrayList<>();

        if (customerName == null) {
            return customerBookings;
        }

        for (CustomBooking booking : getAllBookings()) {
            if (customerName.equalsIgnoreCase(booking.getCustomerName())) {
                customerBookings.add(booking);
            }
        }

        return customerBookings;
    }

    public CustomBooking findBookingById(String id) {
        for (CustomBooking booking : getAllBookings()) {
            if (booking.getBookingId().equals(id)) {
                return booking;
            }
        }

        return null;
    }

    public void updateBooking(CustomBooking updatedBooking) {
        List<CustomBooking> bookings = getAllBookings();
        List<String> updatedLines = new ArrayList<>();

        for (CustomBooking booking : bookings) {
            if (booking.getBookingId().equals(updatedBooking.getBookingId())) {
                updatedLines.add(updatedBooking.toFileString());
            } else {
                updatedLines.add(booking.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }

    public void updateCustomerBooking(CustomBooking updatedBooking, String customerName) {
        CustomBooking existingBooking = findBookingById(updatedBooking.getBookingId());

        if (existingBooking == null || !isOwner(existingBooking, customerName)) {
            return;
        }

        updatedBooking.setCustomerName(existingBooking.getCustomerName());
        updatedBooking.setStatus(existingBooking.getStatus());
        updateBooking(updatedBooking);
    }

    public boolean isOwner(CustomBooking booking, String customerName) {
        return booking != null
                && customerName != null
                && customerName.equalsIgnoreCase(booking.getCustomerName());
    }

    public void deleteBooking(String id) {
        List<CustomBooking> bookings = getAllBookings();
        List<String> updatedLines = new ArrayList<>();

        for (CustomBooking booking : bookings) {
            if (!booking.getBookingId().equals(id)) {
                updatedLines.add(booking.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }

    private String generateBookingId() {
        return "CBK" + System.currentTimeMillis();
    }

    private void normalizeMissingBookingIds() {
        List<String> lines = FileUtil.readLines(FILE_PATH);
        List<String> updatedLines = new ArrayList<>();
        boolean changed = false;
        int counter = 1;

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            CustomBooking booking = CustomBooking.fromFileString(line);
            if (booking.getBookingId() == null || booking.getBookingId().trim().isEmpty()) {
                booking.setBookingId("CBK" + System.currentTimeMillis() + counter);
                changed = true;
            }
            updatedLines.add(booking.toFileString());
            counter++;
        }

        if (changed) {
            FileUtil.overwriteFile(FILE_PATH, updatedLines);
        }
    }
}

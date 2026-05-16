package com.example.bakeryproject.custombooking;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomBookingController {

    private final CustomBookingService bookingService;

    public CustomBookingController(CustomBookingService bookingService) {
        this.bookingService = bookingService;
    }

    private boolean isLoggedIn(HttpSession session) {
        Object role = session.getAttribute("role");
        return "ADMIN".equals(role) || "CUSTOMER".equals(role);
    }

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("role"));
    }

    private boolean isCustomer(HttpSession session) {
        return "CUSTOMER".equals(session.getAttribute("role"));
    }

    private String customerName(HttpSession session) {
        Object name = session.getAttribute("customerName");
        return name == null ? "" : name.toString();
    }

    @GetMapping({
            "/custom-bookings/new", "/custom-bookings/add", "/custom-bookings/create",
            "/custom-booking/new", "/custom-booking/add",
            "/custom-cake", "/custom-cake/new", "/custom-cake-booking",
            "/booking/new", "/booking/add", "/book-custom-cake"
    })
    public String showBookingForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        CustomBooking booking = new CustomBooking();
        if (isCustomer(session)) {
            booking.setCustomerName(customerName(session));
        }

        model.addAttribute("booking", booking);
        return "custombooking/booking-form";
    }

    @PostMapping({
            "/custom-bookings/new", "/custom-bookings/add", "/custom-bookings/save",
            "/custom-booking/new", "/custom-booking/add",
            "/custom-cake/new", "/custom-cake-booking", "/booking/new", "/booking/add"
    })
    public String addBooking(@ModelAttribute CustomBooking booking, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        if (isCustomer(session)) {
            booking.setCustomerName(customerName(session));
        }

        bookingService.addBooking(booking);

        if (isAdmin(session)) {
            return "redirect:/custom-bookings/list";
        }
        return "redirect:/my-bookings";
    }

    @GetMapping({
            "/custom-bookings/list", "/custom-bookings", "/custom-booking/list",
            "/booking/list", "/bookings", "/bookings/list", "/custom-cake/bookings"
    })
    public String showBookingList(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        if (!isAdmin(session)) {
            return "redirect:/my-bookings";
        }
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "custombooking/booking-list";
    }

    @GetMapping({
            "/my-bookings", "/customer-bookings", "/customer/bookings",
            "/customer/booking-list", "/customer/custom-bookings", "/my-custom-bookings"
    })
    public String showCustomerBookingList(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        if (isAdmin(session)) {
            return "redirect:/custom-bookings/list";
        }

        model.addAttribute("bookings", bookingService.getBookingsByCustomerName(customerName(session)));
        return "custombooking/customer-booking-list";
    }

    @GetMapping({"/custom-bookings/edit/{id}", "/custom-booking/edit/{id}", "/booking/edit/{id}"})
    public String showEditBookingPage(@PathVariable String id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        CustomBooking booking = bookingService.findBookingById(id);
        if (booking == null) {
            return isAdmin(session) ? "redirect:/custom-bookings/list" : "redirect:/my-bookings";
        }

        if (isCustomer(session) && !bookingService.isOwner(booking, customerName(session))) {
            return "redirect:/my-bookings";
        }

        model.addAttribute("booking", booking);
        return "custombooking/edit-booking";
    }

    @PostMapping({"/custom-bookings/update", "/custom-booking/update", "/booking/update"})
    public String updateBooking(@ModelAttribute CustomBooking booking, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        if (isAdmin(session)) {
            bookingService.updateBooking(booking);
            return "redirect:/custom-bookings/list";
        }

        bookingService.updateCustomerBooking(booking, customerName(session));
        return "redirect:/my-bookings";
    }

    @GetMapping({"/custom-bookings/delete/{id}", "/custom-booking/delete/{id}", "/booking/delete/{id}"})
    public String deleteBooking(@PathVariable String id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        CustomBooking booking = bookingService.findBookingById(id);

        if (isAdmin(session)) {
            bookingService.deleteBooking(id);
            return "redirect:/custom-bookings/list";
        }

        if (bookingService.isOwner(booking, customerName(session))) {
            bookingService.deleteBooking(id);
        }

        return "redirect:/my-bookings";
    }
}


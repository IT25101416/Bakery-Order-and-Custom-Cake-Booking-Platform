package com.example.bakeryproject.admin;

import com.example.bakeryproject.customer.CustomerService;
import com.example.bakeryproject.product.CakeService;
import com.example.bakeryproject.product.SavouryService;
import com.example.bakeryproject.order.OrderService;
import com.example.bakeryproject.custombooking.CustomBookingService;
import com.example.bakeryproject.review.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final CustomerService customerService;
    private final CakeService cakeService;
    private final SavouryService savouryService;
    private final OrderService orderService;
    private final CustomBookingService customBookingService;
    private final ReviewService reviewService;

    public AdminController(AdminService adminService,
                           CustomerService customerService,
                           CakeService cakeService,
                           SavouryService savouryService,
                           OrderService orderService,
                           CustomBookingService customBookingService,
                           ReviewService reviewService) {
        this.adminService = adminService;
        this.customerService = customerService;
        this.cakeService = cakeService;
        this.savouryService = savouryService;
        this.orderService = orderService;
        this.customBookingService = customBookingService;
        this.reviewService = reviewService;
    }

    @GetMapping({"/dashboard", "/management", "/managements", "/sections"})
    public String dashboard(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        model.addAttribute("customerCount",
                customerService.getAllCustomers().size());
        model.addAttribute("cakeCount", cakeService.getAllCakes().size());
        model.addAttribute("savouryCount",
                savouryService.getAllSavouries().size());
        model.addAttribute("itemCount", cakeService.getAllCakes().size() +
                savouryService.getAllSavouries().size());
        model.addAttribute("orderCount",
                orderService.getAllOrders().size());
        model.addAttribute("bookingCount",
                customBookingService.getAllBookings().size());
        model.addAttribute("reviewCount",
                reviewService.getAllReviews().size());
        model.addAttribute("adminCount",
                adminService.getAllAdmins().size());

        return "admin/admin-dashboard";
    }

    @GetMapping("/register")
    public String showAdminRegisterPage(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        model.addAttribute("admin", new Admin());
        return "admin/admin-register";
    }

    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute Admin admin) {
        adminService.addAdmin(admin);
        return "redirect:/admin/list";
    }

    @GetMapping("/list")
    public String showAdminList(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        model.addAttribute("admins", adminService.getAllAdmins());
        return "admin/admin-panel";
    }

    @GetMapping("/edit/{id}")
    public String showEditAdminPage(@PathVariable String id, Model model,
                                    HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        Admin admin = adminService.findAdminById(id);
        model.addAttribute("admin", admin);
        return "admin/admin-register";
    }

    @PostMapping("/update")
    public String updateAdmin(@ModelAttribute Admin admin, HttpSession
            session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        adminService.updateAdmin(admin);
        return "redirect:/admin/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable String id, HttpSession session)
    {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        adminService.deleteAdmin(id);
        return "redirect:/admin/list";
    }
}


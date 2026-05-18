package com.example.bakeryproject;

import com.example.bakeryproject.admin.AdminService;
import com.example.bakeryproject.customer.Customer;
import com.example.bakeryproject.customer.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class AuthController {
    private final AdminService adminService;
    private final CustomerService customerService;
    public AuthController(AdminService adminService, CustomerService
            customerService) {
        this.adminService = adminService;
        this.customerService = customerService;
    }
    // Public home page
    @GetMapping("/")
    public String publicHome() {
        return "index";
    } // ✅ FIXED: Always open login page (NO redirect here)
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    // Secret admin login page
    @GetMapping({"/staff-secret", "/secret-admin"})
    public String secretAdminLoginPage(HttpSession session, Model model) {
        Object role = session.getAttribute("role");
        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("secretVisible", true);
        return "login";
    }
    // Customer login
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Customer customer = customerService.loginCustomer(username, password);if (customer != null) {
            session.setAttribute("role", "CUSTOMER");
            session.setAttribute("username", username);
            session.setAttribute("customerId", customer.getId());
            session.setAttribute("customerName", customer.getName());
            return "redirect:/products/list"; // after login
        }
        model.addAttribute("error", "Invalid customer username or password");
        return "login";
    }
    // Admin secret login
    @PostMapping("/admin-secret-login")
    public String adminSecretLogin(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpSession session,
                                   Model model) {
        boolean adminLogin = adminService.loginAdmin(username, password);
        if (adminLogin) {
            session.setAttribute("role", "ADMIN");
            session.setAttribute("username", username);
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("secretVisible", true);
        model.addAttribute("adminError", "Invalid admin email or password");
        return "login";
    }
    // Customer home
    @GetMapping("/customer-home")
    public String customerHome(HttpSession session) {
        if (!"CUSTOMER".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        return "customer/customer-home";
    }
    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
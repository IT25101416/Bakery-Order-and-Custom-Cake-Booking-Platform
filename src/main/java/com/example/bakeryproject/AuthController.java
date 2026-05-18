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

    public AuthController(AdminService adminService, CustomerService customerService) {
        this.adminService = adminService;
        this.customerService = customerService;
    }

    @GetMapping({"/", "/home", "/index"})
    public String publicHome() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping({"/staff-secret", "/secret-admin"})
    public String secretAdminLoginPage(HttpSession session, Model model) {
        Object role = session.getAttribute("role");

        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("secretVisible", true);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Customer customer = customerService.loginCustomer(username, password);

        if (customer != null) {
            session.setAttribute("role", "CUSTOMER");
            session.setAttribute("username", username);
            session.setAttribute("customerId", customer.getId());
            session.setAttribute("customerName", customer.getName());

            return "redirect:/products/list";
        }

        model.addAttribute("error", "Invalid customer username or password");
        return "login";
    }

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

    @GetMapping("/customer-home")
    public String customerHome(HttpSession session) {
        if (!"CUSTOMER".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        return "customer/customer-home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
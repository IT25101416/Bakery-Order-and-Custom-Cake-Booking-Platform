package com.example.bakeryproject.customer;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/register";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute Customer customer) {
        customerService.addCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/list")
    public String showCustomerList(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/customer-home";
        }
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditCustomerPage(@PathVariable String id, Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/customer-home";
        }
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/edit-customer";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute Customer customer, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/customer-home";
        }
        customerService.updateCustomer(customer);
        return "redirect:/customers/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable String id, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/customer-home";
        }
        customerService.deleteCustomer(id);
        return "redirect:/customers/list";
    }
}
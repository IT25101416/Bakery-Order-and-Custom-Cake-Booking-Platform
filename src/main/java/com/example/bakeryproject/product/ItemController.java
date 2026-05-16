package com.example.bakeryproject.product;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    private final CakeService cakeService;
    private final SavouryService savouryService;

    public ItemController(CakeService cakeService, SavouryService savouryService) {
        this.cakeService = cakeService;
        this.savouryService = savouryService;
    }

    @GetMapping("/items")
    public String showItemsPage(Model model, HttpSession session) {
        if (session.getAttribute("role") == null) {
            return "redirect:/";
        }

        model.addAttribute("cakes", cakeService.getAllCakes());
        model.addAttribute("savouries", savouryService.getAllSavouries());
        return "product/items";
    }
}


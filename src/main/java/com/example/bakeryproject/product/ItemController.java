package com.example.bakeryproject.product;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/products")
public class ItemController {

    private final CakeService cakeService;

    public ItemController(CakeService cakeService) {
        this.cakeService = cakeService;
    }

    @GetMapping("/add")
    public String showAddCakePage(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        model.addAttribute("cake", new Cake());
        return "product/add-cake";
    }

    @PostMapping("/add")
    public String addCake(@ModelAttribute Cake cake) {
        cakeService.addCake(cake);
        return "redirect:/products/list";
    }

    @GetMapping("/list")
    public String showCakeList(Model model, HttpSession session) {
        if (session.getAttribute("role") == null) {
            return "redirect:/";
        }

        model.addAttribute("cakes", cakeService.getAllCakes());
        return "product/cake-list";
    }


    @GetMapping({"/categories", "/category", "/categorize", "/catagarize"})
    public String showCategoriesPage(Model model, HttpSession session) {
        return showCakeList(model, session);
    }

    @GetMapping("/edit/{id}")
    public String showEditCakePage(@PathVariable String id, Model model) {
        Cake cake = cakeService.findCakeById(id);
        model.addAttribute("cake", cake);
        return "product/edit-cake";
    }

    @PostMapping("/update")
    public String updateCake(@ModelAttribute Cake cake) {
        cakeService.updateCake(cake);
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCake(@PathVariable String id, HttpSession session)
    {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        cakeService.deleteCake(id);
        return "redirect:/products/list";
    }
}


package com.example.bakeryproject.product;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/savouries")
public class SavouryController {

    private final SavouryService savouryService;

    public SavouryController(SavouryService savouryService) {
        this.savouryService = savouryService;
    }

    @GetMapping("/list")
    public String showSavouryList(Model model, HttpSession session) {
        if (session.getAttribute("role") == null) {
            return "redirect:/";
        }

        model.addAttribute("savouries", savouryService.getAllSavouries());
        return "product/savoury-list";
    }

    @GetMapping("/add")
    public String showAddSavouryPage(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        model.addAttribute("savoury", new Savoury());
        return "product/add-savoury";
    }

    @PostMapping("/add")
    public String addSavoury(@ModelAttribute Savoury savoury, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        savouryService.addSavoury(savoury);
        return "redirect:/savouries/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditSavouryPage(@PathVariable String id, Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        model.addAttribute("savoury", savouryService.findSavouryById(id));
        return "product/edit-savoury";
    }

    @PostMapping("/update")
    public String updateSavoury(@ModelAttribute Savoury savoury, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        savouryService.updateSavoury(savoury);
        return "redirect:/savouries/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSavoury(@PathVariable String id, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        savouryService.deleteSavoury(id);
        return "redirect:/savouries/list";
    }
}

package com.example.lab2.controller;

import com.example.lab2.model.Tale;
import com.example.lab2.service.TaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class TaleController {

    @Autowired
    private TaleService service;

    @GetMapping
    public String index(Model model, @RequestParam(required = false) String q) {
        if (q != null && !q.isEmpty()) {
            model.addAttribute("tales", service.search(q));
        } else {
            model.addAttribute("tales", service.getAll());
        }
        model.addAttribute("status", service.getStatus());

        model.addAttribute("newTale", new Tale());

        return "index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Tale tale) {
        service.save(tale);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable int id, Model model) {
        Tale tale = service.getById(id);
        if (tale == null) return "redirect:/";
        model.addAttribute("tale", tale);
        return "edit";
    }

    @PostMapping("/save")
    public String saveTale(@ModelAttribute Tale tale) {
        service.save(tale);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.remove(id);
        return "redirect:/";
    }
}
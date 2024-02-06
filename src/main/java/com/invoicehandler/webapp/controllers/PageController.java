package com.invoicehandler.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping
    public String showIndex(Model model){
        model.addAttribute("title", "Profile");
        return "index";
    }

    @GetMapping("/admin")
    public String displayAdmin(Model model) {

        model.addAttribute("title", "Admin");

        return "admin";
    }

}

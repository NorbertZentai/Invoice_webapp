package com.invoicehandler.webapp.controllers;

import com.invoicehandler.webapp.models.LoginModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String displayLoginForm(Model model){

        model.addAttribute("title", "Login");
        model.addAttribute("loginModel", new LoginModel());

        return "login";
    }

    @PostMapping("/processLogin")
    public String processLogin(@Valid LoginModel loginModel, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("loginModel", loginModel);
            return "login";
        }

        model.addAttribute("loginModel",loginModel);
        return "loginResults";
    }
}

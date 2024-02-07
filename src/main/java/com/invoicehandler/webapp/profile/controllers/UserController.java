package com.invoicehandler.webapp.profile.controllers;

import com.invoicehandler.webapp.models.LoginModel;
import com.invoicehandler.webapp.models.RegistrationModel;
import com.invoicehandler.webapp.models.UserModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserController {

    @GetMapping("/index")
    public String displayIndex(Model model){

        model.addAttribute("title", "Profile");

        return "index";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {

        model.addAttribute("title", "Login");
        model.addAttribute("userModel", new UserModel());

        return "login";
    }

    @PostMapping("/processLogin")
    public String processLogin(@Valid UserModel userModel, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userModel", userModel);
            return "login";
        }

        model.addAttribute("userModel", userModel);
        return "index";
    }

    @GetMapping("/registration")
    public String displayRegistrationForm(Model model) {

        model.addAttribute("title", "Registration");
        model.addAttribute("userModel", new UserModel());

        return "registration";
    }

    @PostMapping("/processRegistration")
    public String processRegistration(@Valid UserModel userModel, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userModel", userModel);
            return "registration";
        }

        model.addAttribute("userModel", userModel);

        return "registrationResult";
    }
}
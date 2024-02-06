package com.invoicehandler.webapp.controllers;

import com.invoicehandler.webapp.models.RegistrationModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegisterController {

    @GetMapping
    public String displayRegistrationForm(Model model){

        model.addAttribute("title", "Registration");
        model.addAttribute("registrationModel", new RegistrationModel());

        return "registration";
    }

    @PostMapping("/processRegistration")
    public String processRegistration(@Valid RegistrationModel registrationModel, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationModel", registrationModel);
            return "registration";
        }

        model.addAttribute("registrationModel",registrationModel);

        return "registrationResult";
    }
}

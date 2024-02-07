package com.invoicehandler.webapp.user.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.invoicehandler.webapp.models.RoleModel;
import com.invoicehandler.webapp.models.UserModel;
import com.invoicehandler.webapp.role.service.RoleService;
import com.invoicehandler.webapp.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping
public class UserController {
    UserService userService;
    RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public String displayIndex(Model model){
        model.addAttribute("title", "Profile");

        return "index";
    }

    @GetMapping("/login")
    public String displayLogin(Model model) {

        model.addAttribute("title", "Login");
        model.addAttribute("userModel", new UserModel());

        return "login";
    }

    @PostMapping("/processLogin")
    public String login(@Valid UserModel userModel, Model model) {

        UserModel user = userService.searchExactUser(userModel.getUsername());

        if( user == null) {
            userModel.setPassword(null);
            model.addAttribute("mainTitle", "Wrong username or password!");
            model.addAttribute("userModel", userModel);
            return "/login";
        }

        BCrypt.Result result = BCrypt.verifyer().verify(userModel.getPassword().toCharArray(), user.getPassword());

        if(!result.verified){
            userModel.setPassword(null);
            model.addAttribute("mainTitle", "Wrong username or password!");
            model.addAttribute("userModel", userModel);
            return "/login";
        }

        user.setLastLogin(LocalDate.now().toString());

        List<UserModel> users = userService.getItems();
        List<RoleModel> roles = roleService.getItems();
        userModel.setPassword(null);

        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        model.addAttribute("title", "Admin page");

        return "/admin";
    }

    @GetMapping("/signUp")
    public String displaySignUp(Model model) {

        model.addAttribute("title", "Registration");
        model.addAttribute("userModel", new UserModel());

        return "signUp";
    }

    @PostMapping("/processSignUp")
    public String registration(@Valid UserModel userModel, Model model) {

        if(!Objects.equals(userModel.getPassword(), userModel.getRePassword())){
            userModel.setPassword(null);
            userModel.setRePassword(null);
            model.addAttribute("userModel", userModel);
            model.addAttribute("error", "The passwords did not match!");
            return "/signUp";
        }

        if(userService.searchUser(userModel.getUsername()) != null){
            userModel.setPassword(null);
            userModel.setRePassword(null);
            model.addAttribute("userModel", userModel);
            model.addAttribute("error", "There is already a user with a similar name!");
            return "/signUp";
        }

        userModel.setPassword(BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()));
        userModel.setLastLogin(LocalDate.now().toString());
        userModel.setRole("user");

        userService.addItem(userModel);

        model.addAttribute("title", "Login");
        model.addAttribute("mainTitle", "Successful registration! Let's log in!");
        model.addAttribute("error", null);
        model.addAttribute("users", userModel);

        return "/login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {

        model.addAttribute("userModel", new UserModel());

        return "login";
    }
}
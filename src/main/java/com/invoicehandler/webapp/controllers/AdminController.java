package com.invoicehandler.webapp.controllers;

import com.invoicehandler.webapp.models.RoleModel;
import com.invoicehandler.webapp.models.UserModel;
import com.invoicehandler.webapp.user.services.UserService;
import com.invoicehandler.webapp.role.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
        UserService userService;
        RoleService roleService;

        @Autowired
        public AdminController(UserService userService, RoleService roleService) {
            this.userService = userService;
            this.roleService = roleService;
        }
        @GetMapping
        public String displayAdmin(Model model) {
            List<UserModel> users = userService.getItems();

            List<RoleModel> roles = roleService.getItems();

            model.addAttribute("title", "Admin page");
            model.addAttribute("roles", roles);
            model.addAttribute("users", users);

            return "admin";
        }

    @PostMapping("/delete")
    public String deleteRole(@Valid UserModel userModel, Model model) {

        System.out.println(userModel);

        UserModel user = userService.getById(userModel.getId());

        if(userService.deleteItem(userModel.getId())){
            model.addAttribute("mainTitle", "Successfully deleted the user " + user.getUsername() + "!");
        } else{
            model.addAttribute("mainTitle", null);
            model.addAttribute("error", "Something went wrong!");
        }

        List<UserModel> users = userService.getItems();
        List<RoleModel> roles = roleService.getItems();

        model.addAttribute("title", "Admin page");
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        return "admin";
    }

    @PostMapping("/editRole")
    public String editForm(UserModel userModel, RoleModel roleModel, Model model){

        UserModel user = userService.getById(userModel.getId());

        user.setRole(userModel.getRole());
         if(userService.updateItem(userModel.getId(), user) != null){
             model.addAttribute("mainTitle", "Successfully changed role! " + user.getUsername() +
                     " is now " + user.getRole() + "!");
         } else {
             model.addAttribute("mainTitle", null);
             model.addAttribute("error", "Something went wrong!");
         }

        List<UserModel> users = userService.getItems();
        List<RoleModel> roles = roleService.getItems();

        model.addAttribute("roles", roles);
        model.addAttribute("users", users);

        return "admin";
    }
}

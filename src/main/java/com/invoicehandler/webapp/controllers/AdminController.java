package com.invoicehandler.webapp.controllers;

import com.invoicehandler.webapp.models.RoleModel;
import com.invoicehandler.webapp.models.UserModel;
import com.invoicehandler.webapp.user.services.UserService;
import com.invoicehandler.webapp.role.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        public String displayAdmin(Model model, HttpServletRequest req) {

            UserModel user = (UserModel) req.getSession().getAttribute("userSession");
            if(user == null || !user.getRole().equals("admin")){
                model.addAttribute("title", "Login");
                model.addAttribute("userModel", new UserModel());

                return "login";
            }

            List<UserModel> users = userService.getItems();
            List<RoleModel> roles = roleService.getItems();

            model.addAttribute("title", "Admin page");
            model.addAttribute("roles", roles);
            model.addAttribute("users", users);

            return "admin";
        }

    @PostMapping("/delete")
    public String deleteRoles(@RequestParam("selectedIds") List<Integer> selectedIds, Model model) {
        if (selectedIds.isEmpty()) {
            model.addAttribute("mainTitle", null);
            model.addAttribute("error", "No users selected for deletion.");
        } else {
            for (int userId : selectedIds) {
                userService.deleteItem(userId);
            }
            model.addAttribute("mainTitle", "Successfully deleted selected users!");
            model.addAttribute("error", null);
        }

        List<UserModel> users = userService.getItems();
        List<RoleModel> roles = roleService.getItems();

        model.addAttribute("title", "Admin page");
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        return "admin";
    }

    @PostMapping("/editRole")
    public String editForm(@ModelAttribute UserModel userModel, Model model){

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

package com.invoicehandler.webapp.controllers;

import com.invoicehandler.webapp.models.RoleModel;
import com.invoicehandler.webapp.models.UserModel;
import com.invoicehandler.webapp.user.services.UserService;
import com.invoicehandler.webapp.role.service.RoleService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        public String displayAdmin(Model model, HttpServletRequest req,
                                   @ModelAttribute("mainTitle") String mainTitle,
                                   @ModelAttribute("error") String error) {

            UserModel user = (UserModel) req.getSession().getAttribute("userSession");

            if(user == null || !user.getRole().equals("admin")){
                return "redirect:login";
            }

            List<UserModel> users = userService.getItems();
            List<RoleModel> roles = roleService.getItems();

            model.addAttribute("title", "Admin page");
            model.addAttribute("error", (error.isEmpty()?null:error));
            model.addAttribute("mainTitle", (mainTitle.isEmpty()?null:mainTitle));
            model.addAttribute("roles", roles);
            model.addAttribute("users", users);

            return "admin";
        }

    @PostMapping("/delete")
    public String deleteRoles(@RequestParam("selectedIds") List<Integer> selectedIds, Model model) {
        if (selectedIds.isEmpty()) {
            model.addAttribute("error", "No users selected for deletion.");
        } else {
            for (int userId : selectedIds) {
                userService.deleteItem(userId);
            }
            model.addAttribute("mainTitle", "Successfully deleted selected users!");
        }

        return "redirect:/admin";
    }

    @PostMapping("/editRole")
    public String editForm(@ModelAttribute UserModel userModel, RedirectAttributes redirectAttributes){

        UserModel user = userService.getById(userModel.getId());

        user.setRole(userModel.getRole());
        if(userService.updateItem(userModel.getId(), user) != null){
            redirectAttributes.addFlashAttribute("mainTitle", "Successfully changed role! " + user.getUsername() +
                    " is now " + user.getRole() + "!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Something went wrong!");
        }

        return "redirect:/admin";
    }

}

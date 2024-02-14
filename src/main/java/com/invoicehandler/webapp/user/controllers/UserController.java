package com.invoicehandler.webapp.user.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.invoicehandler.webapp.ReCaptcha.ReCaptchaService;
import com.invoicehandler.webapp.models.UserModel;
import com.invoicehandler.webapp.role.service.RoleService;
import com.invoicehandler.webapp.user.services.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Objects;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Controller
@RequestMapping
public class UserController {
    UserService userService;
    RoleService roleService;

    @Autowired
    private ReCaptchaService validator;
    private int failedLoginAttempts = 0;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public String displayIndex(Model model, HttpServletRequest req){
        UserModel user = (UserModel) req.getSession().getAttribute("userSession");
        if(user == null){
            model.addAttribute("failedLoginAttempts", failedLoginAttempts);
            model.addAttribute("title", "Login");
            model.addAttribute("userModel", new UserModel());

            return "login";
        }

        model.addAttribute("title", "Profile");
        model.addAttribute("user", new UserModel());

        return "index";

    }

    @GetMapping
    public String index(Model model, HttpServletRequest req){
        UserModel user = (UserModel) req.getSession().getAttribute("userSession");
        if(user == null){
            model.addAttribute("failedLoginAttempts", failedLoginAttempts);
            model.addAttribute("title", "Login");
            model.addAttribute("userModel", new UserModel());

            return "login";
        }

        model.addAttribute("title", "Profile");
        model.addAttribute("user", new UserModel());

        return "index";
    }

    @PostMapping("/passwordUpdate")
    public String updatePassword(@Valid UserModel userModel, BindingResult bindingResult,
                                 Model model, HttpServletRequest req){

        UserModel user = userService.getById(((UserModel) req.getSession().getAttribute("userSession")).getId());

        BCrypt.Result result = BCrypt.verifyer().verify(userModel.getPassword().toCharArray(), user.getPassword());
        System.out.println("alma");
        if(!result.verified){
            userModel.setPassword(null);
            userModel.setNewPassword(null);
            userModel.setReNewPassword(null);
            model.addAttribute("error", "Wrong current password!");
            model.addAttribute("user", userModel);
            return "index";
        }

        if(!userModel.getNewPassword().equals(userModel.getReNewPassword())){
            userModel.setNewPassword(null);
            userModel.setReNewPassword(null);
            model.addAttribute("error", "New passwords do not match!");
            model.addAttribute("user", userModel);
            return "index";
        }

        user.setPassword(BCrypt.withDefaults().hashToString(12, userModel.getNewPassword().toCharArray()));
        userService.updateItem(user.getId(), user);

        model.addAttribute("user", new UserModel());
        model.addAttribute("mainTitle", "Old password successfully changed!");
        model.addAttribute("title", "Admin page");

        return "index";
    }

    @GetMapping("/login")
    public String displayLogin(Model model) {
        model.addAttribute("failedLoginAttempts", failedLoginAttempts);
        model.addAttribute("title", "Login");
        model.addAttribute("userModel", new UserModel());

        return "login";
    }

    @PostMapping("/processLogin")
    public String login(@Valid UserModel userModel, Model model,
                        HttpServletRequest request, @Nullable @RequestParam(name="g-recaptcha-response") String captcha) {

        if(failedLoginAttempts > 3){
            if(!validator.validateCaptcha(captcha)){
                userModel.setPassword(null);
                model.addAttribute("error", "Please Verify Captcha");
                model.addAttribute("user", userModel);
                model.addAttribute("failedLoginAttempts", failedLoginAttempts);
                return "login";
            }
        }

        UserModel user = userService.searchExactUser(userModel.getUsername());

        if( user == null) {
            userModel.setPassword(null);
            failedLoginAttempts++;
            model.addAttribute("error", "Wrong username or password!");
            model.addAttribute("user", userModel);
            model.addAttribute("failedLoginAttempts", failedLoginAttempts);
            return "login";
        } else{
            BCrypt.Result result = BCrypt.verifyer().verify(userModel.getPassword().toCharArray(), user.getPassword());

            if(!result.verified){
                userModel.setPassword(null);
                failedLoginAttempts++;
                model.addAttribute("error", "Wrong username or password!");
                model.addAttribute("user", userModel);
                model.addAttribute("failedLoginAttempts", failedLoginAttempts);
                return "login";
            }
        }

        failedLoginAttempts=0;
        user.setLastLogin(LocalDate.now().toString());

        HttpSession session = request.getSession();
        session.setAttribute("userSession", user);

        userModel.setPassword(null);
        model.addAttribute("user", new UserModel());
        model.addAttribute("title", "Admin page");

        return "index";
    }

    @GetMapping("/signUp")
    public String displaySignUp(Model model) {

        model.addAttribute("title", "Registration");
        model.addAttribute("userModel", new UserModel());

        return "signUp";
    }

    @PostMapping("/processSignUp")
    public String registration(@Valid UserModel userModel, Model model) {

        if(userService.searchUser(userModel.getUsername()) != null){
            userModel.setPassword(null);
            userModel.setRePassword(null);
            model.addAttribute("userModel", userModel);
            model.addAttribute("error", "This username cannot be used!");
            return "signUp";
        }

        if(!Objects.equals(userModel.getPassword(), userModel.getRePassword())){
            userModel.setPassword(null);
            userModel.setRePassword(null);
            model.addAttribute("userModel", userModel);
            model.addAttribute("error", "The passwords did not match!");
            return "signUp";
        }

        userModel.setPassword(BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()));
        userModel.setLastLogin(LocalDate.now().toString());
        userModel.setRole("user");

        userService.addItem(userModel);

        model.addAttribute("failedLoginAttempts", failedLoginAttempts);
        model.addAttribute("title", "Login");
        model.addAttribute("mainTitle", "Successful registration! Let's log in!");
        model.addAttribute("error", null);
        model.addAttribute("users", userModel);

        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest req) {
        model.addAttribute("failedLoginAttempts", failedLoginAttempts);
        model.addAttribute("userModel", new UserModel());
        req.getSession().invalidate();

        return "login";
    }
}

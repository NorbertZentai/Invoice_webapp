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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            return "redirect:login";
        }

        model.addAttribute("title", "Profile");
        model.addAttribute("user", new UserModel());

        return "index";

    }

    @GetMapping
    public String index(Model model, HttpServletRequest req){
        UserModel user = (UserModel) req.getSession().getAttribute("userSession");
        if(user == null){
            return "redirect:login";
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
    public String displayLogin(Model model, @ModelAttribute("mainTitle") String mainTitle,
                               @ModelAttribute("error") String error,
                               @ModelAttribute("userModel") UserModel userModel){

        model.addAttribute("failedLoginAttempts", failedLoginAttempts);
        model.addAttribute("title", "Login");
        model.addAttribute("mainTitle", (mainTitle.isEmpty() ?null:mainTitle));
        model.addAttribute("error", (error.isEmpty() ?null:error));
        model.addAttribute("userModel", (userModel==null)?new UserModel():userModel);

        return "login";
    }

    @PostMapping("/processLogin")
    public String login(@Valid UserModel userModel, Model model,
                        HttpServletRequest request,
                        @Nullable @RequestParam(name="g-recaptcha-response") String captcha,
                        RedirectAttributes redirectAttributes) {

        if(failedLoginAttempts > 2){
            if(!validator.validateCaptcha(captcha)){
                userModel.setPassword(null);
                redirectAttributes.addFlashAttribute("error", "Please Verify Captcha");
                redirectAttributes.addFlashAttribute("userModel", userModel);
                redirectAttributes.addFlashAttribute("failedLoginAttempts", failedLoginAttempts);
                return "redirect:login";
            }
        }

        UserModel user = userService.searchExactUser(userModel.getUsername());

        if( user == null) {
            userModel.setPassword(null);
            failedLoginAttempts++;
            redirectAttributes.addFlashAttribute("error", "Wrong username or password!");
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("failedLoginAttempts", failedLoginAttempts);
            return "redirect:login";
        } else{
            BCrypt.Result result = BCrypt.verifyer().verify(userModel.getPassword().toCharArray(), user.getPassword());

            if(!result.verified){
                userModel.setPassword(null);
                failedLoginAttempts++;
                redirectAttributes.addFlashAttribute("error", "Wrong username or password!");
                redirectAttributes.addFlashAttribute("userModel", userModel);
                redirectAttributes.addFlashAttribute("failedLoginAttempts", failedLoginAttempts);
                return "redirect:login";
            }
        }

        failedLoginAttempts=0;
        user.setLastLogin(LocalDate.now().toString());

        HttpSession session = request.getSession();
        session.setAttribute("userSession", user);

        return "redirect:index";
    }

    @GetMapping("/signUp")
    public String displaySignUp(Model model, @ModelAttribute("userModel") UserModel userModel,
                                @ModelAttribute("error") String error) {

        model.addAttribute("title", "Registration");
        model.addAttribute("error", (error.isEmpty()?null:error));
        model.addAttribute("userModel", (userModel==null?new UserModel():userModel));

        return "signUp";
    }

    @PostMapping("/processSignUp")
    public String registration(@Valid UserModel userModel, Model model, RedirectAttributes redirectAttributes) {

        if(userService.searchUser(userModel.getUsername()) != null){
            userModel.setPassword(null);
            userModel.setRePassword(null);
            redirectAttributes.addFlashAttribute("error", "This username cannot be used!");
            redirectAttributes.addFlashAttribute("userModel", userModel);
            return "redirect:signUp";
        }

        if(!Objects.equals(userModel.getPassword(), userModel.getRePassword())){
            userModel.setPassword(null);
            userModel.setRePassword(null);
            redirectAttributes.addFlashAttribute("error", "The passwords did not match!");
            redirectAttributes.addFlashAttribute("userModel", userModel);
            return "redirect:signUp";
        }

        userModel.setPassword(BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()));
        userModel.setLastLogin(LocalDate.now().toString());
        userModel.setRole("user");

        userService.addItem(userModel);

        redirectAttributes.addFlashAttribute("mainTitle", "Successful registration! Let's log in!");
        redirectAttributes.addFlashAttribute("userModel", userModel);

        return "redirect:login";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest req) {
        model.addAttribute("userModel", new UserModel());
        req.getSession().invalidate();

        return "redirect:login";
    }
}

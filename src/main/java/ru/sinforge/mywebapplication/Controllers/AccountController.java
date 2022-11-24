package ru.sinforge.mywebapplication.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Services.UserService;
import ru.sinforge.mywebapplication.ViewModels.UserViewModel;

@Controller
public class AccountController {
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String Registration() {
        logger.info("User start registration");
        return "registration";
    }

    @PostMapping("/registration")
    public String Registration(UserViewModel user) {
        if(!userService.AddUser(user.getUsername(), user.getPassword())) {
            logger.info("User enter existing nickname on registration");
            return "registration";
        }
        logger.info("New user successful added");
        return "login";
    }

    @GetMapping("/profile")
    public String Profile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String Profile(Long id, UserViewModel user, @RequestParam("description") String description, @RequestParam("img") MultipartFile img) {
        userService.changeData(id, user, description, img);
        return "redirect:/profile";
    }

    @GetMapping("/user")
    public String GetUserProfile(@RequestParam(name="id", required = true) Long id, Model model) {
        model.addAttribute("user", userService.GetUserById(id));
        return "user_profile";
    }

}

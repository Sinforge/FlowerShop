package ru.sinforge.mywebapplication.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "auth";
    }



    @GetMapping(value = "/auth")
    public String Authorization() {
        return "auth";
    }


}

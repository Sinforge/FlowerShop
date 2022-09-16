package ru.sinforge.mywebapplication.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Services.UserService;

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

    @PutMapping("/aboba")
    public String Aboba() {
        return "aboba";
    }

    @PostMapping("/registration")
    public String AddUser(User user) {
        if(!userService.AddUser(user.getUsername(), user.getPassword())) {
            logger.info("User enter existing nickname on registration");
            return "registration";
        }
        logger.info("New user successful added");
        return "registration";
    }
}

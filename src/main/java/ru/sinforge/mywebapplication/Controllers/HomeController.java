package ru.sinforge.mywebapplication.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public String HomePage(Model model) {

        return "Its homepage";
    }

}

package ru.sinforge.mywebapplication.Controllers;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sinforge.mywebapplication.Services.HoroscopeAPI;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home (Model model) {

        return "home";
    }
}

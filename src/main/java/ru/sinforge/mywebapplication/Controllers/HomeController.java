package ru.sinforge.mywebapplication.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public String HomePage(Model model) {

        return "home";
    }

    @PostMapping(value = "/GetYourHoroscope")
    public String GetApiInfo(Model model, @RequestParam(value = "sign") String sign, @RequestParam(value = "day") String day)
    {
        //Build request to Horoscope API
        //HoroscopeAPI horoscopeAPI = new HoroscopeAPI(sign, day);

        //Add to model data from api response
        //model.addAttribute("ApiResponse", horoscopeAPI.GetInfoFromAPI());

        return "result";
    }
}

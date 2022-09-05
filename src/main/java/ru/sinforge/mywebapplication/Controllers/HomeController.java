package ru.sinforge.mywebapplication.Controllers;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.sinforge.mywebapplication.Models.HoroscopeModel;
import ru.sinforge.mywebapplication.Services.HoroscopeAPI;



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
        HoroscopeAPI horoscopeAPI = new HoroscopeAPI(sign, day);

        //Add to model data from api response
        model.addAttribute("ApiResponse", horoscopeAPI.GetInfoFromAPI());

        return "result";
    }
}

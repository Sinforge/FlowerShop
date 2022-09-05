package ru.sinforge.mywebapplication.Controllers;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.sinforge.mywebapplication.Models.HoroscopeModel;
import ru.sinforge.mywebapplication.Services.HoroscopeAPI;



@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home (Model model) {
        //Build request to Horoscope API
        HoroscopeAPI horoscopeAPI = new HoroscopeAPI();

        //Add to model data from api response
        model.addAttribute("ApiResponse", horoscopeAPI.GetInfoFromAPI());
        return "home";
    }
}

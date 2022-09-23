package ru.sinforge.mywebapplication.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sinforge.mywebapplication.Services.FlowerService;

import java.util.concurrent.ExecutionException;

@Controller
public class FlowerController {
    private FlowerService flowerService;


    FlowerController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping("/flower/{id}")
    public String FlowerPage(@PathVariable("id") String id, Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("flower", flowerService.getFlower(id));
        return "flower_page";
    }
}

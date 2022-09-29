package ru.sinforge.mywebapplication.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.FlowerBouquet;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Services.FlowerService;
import ru.sinforge.mywebapplication.Services.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;

@Controller
public class FlowerController {
    private FlowerService flowerService;
    private UserService userService;


    FlowerController(FlowerService flowerService, UserService userService) {
        this.userService = userService;
        this.flowerService = flowerService;
    }

    @GetMapping("/flower")
    public String FlowerPage(@AuthenticationPrincipal User user, @RequestParam(value = "id", required = true) String id, Model model) throws ExecutionException, InterruptedException {

        model.addAttribute("userIsAuth", user == null);
        if(user != null) {
            model.addAttribute("FlowerInBasket", StreamSupport.stream(
                            userService.GetUserFlowerBasket(user).spliterator(), false)
                    .anyMatch(Flower -> id.equals(Flower.getFlowerId())));
        }
        model.addAttribute("flower", flowerService.getFlower(id));
        return "flower_page";
    }

    @PostMapping("/AddToBasket")
    public String AddToBasket(@AuthenticationPrincipal User user, String FlowerId) {
        userService.AddBouquet(user, FlowerId);
        return "redirect:/";

    }


    @GetMapping("/basket")
    public String UserBasket(@AuthenticationPrincipal User user, Model model) throws ExecutionException, InterruptedException {
        Iterable<FlowerBouquet> Bouquets = userService.GetUserFlowerBasket(user);
        List<Flower> flowerList = new ArrayList<>();

        for (FlowerBouquet bouquet:
             Bouquets) {
            flowerList.add(flowerService.getFlower(bouquet.getFlowerId()));
        }

        model.addAttribute("list_of_flowers", flowerList);
        return "user_basket";
    }

}
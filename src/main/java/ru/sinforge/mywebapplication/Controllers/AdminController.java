package ru.sinforge.mywebapplication.Controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.Role;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Services.FlowerService;

import java.util.concurrent.ExecutionException;

@Controller
public class AdminController {
    private FlowerService flowerService;


    public AdminController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping("/create")
    public String createFlower(@AuthenticationPrincipal User user, Model model) {
        IsAuth(user, model);
        if (user.getRoles().contains(Role.Administrator)) {
            return "create_flower";
        } else {
            model.addAttribute("exception", "Access denied");
            return "exception-page";
        }
    }


    @PostMapping("/create")
    public String createFlower(
            @AuthenticationPrincipal User user, Flower flower,
            @RequestParam("img") MultipartFile img, Model model) throws ExecutionException, InterruptedException {
        IsAuth(user, model);

        if (user.getRoles().contains(Role.Administrator)) {
            if (flowerService.createFlower(flower, img)) {
                model.addAttribute("FlowerList", flowerService.getAllFlowers());
                return "get_all_flowers";
            }
        } else {
            model.addAttribute("exception", "Access denied");
            return "exception-page";
        }
        model.addAttribute("exception", "Flower with this name exist ---> create" +
                " flower this different name or update existing flower");
        return "exception-page";
    }


    public String getFlower(String flower_id) throws ExecutionException, InterruptedException {
        return flowerService.getFlower(flower_id).toString();
    }


    @PutMapping("/update")
    public String updateFlower(@RequestBody Flower flower) {
        if (flowerService.updateFlower(flower)) {
            return "get_all_flowers";
        }

        return "exception-page";
    }

    @PutMapping("/delete")
    public String deleteFlower(@RequestBody String flower_id) {
        return flowerService.deleteFlower(flower_id);
    }

    @GetMapping({"/", "/home"})
    public String getAllFlowers(@AuthenticationPrincipal User user, Model model) {
        IsAuth(user, model);
        Iterable<Flower> flowers = flowerService.getAllFlowers();
        model.addAttribute("FlowerList", flowers);
        return "get_all_flowers";
    }



    //@GetMapping("/basket")
  //  public String ShoppingBasket(@AuthenticationPrincipal User user, Model model) {

//    }

    public void IsAuth(User user, Model model) {
        if(user == null) {
            model.addAttribute("user", "NotAuth");
            return;
        }
        model.addAttribute("user", "Auth");
    }

}


package ru.sinforge.mywebapplication.Controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Services.FlowerService;

import java.util.concurrent.ExecutionException;

@Controller
public class AdminController {
    private FlowerService flowerService;



    public AdminController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping("/create")
    public String createFlower() {
        return "create_flower";
    }


    @PostMapping("/create")
    public String createFlower(Flower flower, @RequestParam("img")MultipartFile img, Model model) throws ExecutionException, InterruptedException {
        if (flowerService.createFlower(flower, img)) {
            model.addAttribute("FlowerList", flowerService.getAllFlowers());
            return "get_all_flowers";
        }
        model.addAttribute("exception", "Flower with this name exist ---> create" +
                " flower this different name or update existing flower");
        return "exception-page";
    }


    @GetMapping("/get")
    public String getFlower(@RequestParam String flower_id) throws ExecutionException, InterruptedException {
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

    @GetMapping("/get-all")
    public String getAllFlowers(Model model) {
        Iterable<Flower> flowers = flowerService.getAllFlowers();
        model.addAttribute("FlowerList", flowers);
        return "get_all_flowers";
    }

    @GetMapping("/test")
    public ResponseEntity<String> testGetEndpoint() {return ResponseEntity.ok("OK");}
}


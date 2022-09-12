package ru.sinforge.mywebapplication.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String createFlower(Flower flower) throws ExecutionException, InterruptedException {
        flowerService.createFlower(flower);
        return "get_all_flowers";
    }

    @GetMapping("/get")
    public String getFlower(@RequestParam String flower_id) throws ExecutionException, InterruptedException {
        return flowerService.getFlower(flower_id).toString();
    }


    @PutMapping("/update")
    public String updateFlower(@RequestBody Flower flower) {
        return flowerService.updateFlower(flower);
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


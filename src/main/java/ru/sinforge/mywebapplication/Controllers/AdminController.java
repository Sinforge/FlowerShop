package ru.sinforge.mywebapplication.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Services.FlowerService;

import java.util.concurrent.ExecutionException;

@RestController
public class AdminController {
    private FlowerService flowerService;

    public AdminController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @PostMapping("/create")
    public String createFlower(@RequestBody Flower flower) throws ExecutionException, InterruptedException {
        return flowerService.createFlower(flower);
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

    @GetMapping("/test")
    public ResponseEntity<String> testGetEndpoint() {return ResponseEntity.ok("OK");}
}


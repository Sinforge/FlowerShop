package ru.sinforge.mywebapplication.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Services.CommentService;
import ru.sinforge.mywebapplication.Services.FlowerService;
import ru.sinforge.mywebapplication.Services.UserService;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class ApiController {
    private FlowerService flowerService;
    private UserService userService;
    private CommentService commentService;

    ApiController(FlowerService flowerService, UserService userService, CommentService commentService) {
        this.userService = userService;
        this.flowerService = flowerService;
        this.commentService = commentService;
    }
    public class FlowerRating {
        public Flower flower;
        public Double rating;
        public FlowerRating(Flower flower, Double rating) {
            this.flower = flower;
            this.rating = rating;
        }
    }
    @GetMapping("/get-collection-flowers")
    public Iterable<FlowerRating> getFlowersCollection() {
        Iterable<Flower> flowers = flowerService.getAllFlowers();
        ArrayList<FlowerRating> flowerRatingList = new ArrayList<>();
        //get rating for all flowers
        for (Flower flower: flowers) {
            flowerRatingList.add(new FlowerRating(flower, flowerService.getSummaryRating(flower.getId())));
        }
        return flowerRatingList;

    }
}

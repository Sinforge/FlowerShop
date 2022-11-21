package ru.sinforge.mywebapplication.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Services.CommentService;
import ru.sinforge.mywebapplication.Services.FlowerService;
import ru.sinforge.mywebapplication.Services.UserService;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
            Double rating = flowerService.getSummaryRating(flower.getId());
            if (rating.isNaN()) {
                rating = 0.0;
            }
            flowerRatingList.add(new FlowerRating(flower, rating    ));
        }
        return flowerRatingList;

    }
    @PostMapping("/deleteComment")
    public String deleteComment(Long commentId, String flowerId, Model model) throws ExecutionException, InterruptedException {
        commentService.DeleteComment(commentId);
        model.addAttribute("comments",commentService.GetAllCommentsOnFlowerPage(flowerId));
        model.addAttribute("flower", flowerService.getFlower(flowerId));
        return "comments_div";
    }

}

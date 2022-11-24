package ru.sinforge.mywebapplication.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sinforge.mywebapplication.Entities.Comment;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Services.CommentService;
import ru.sinforge.mywebapplication.Services.FlowerService;
import ru.sinforge.mywebapplication.Services.UserService;
import ru.sinforge.mywebapplication.ViewModels.CommentViewModel;

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
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/LeaveComment")
    public Iterable<Comment> LeaveComment(@AuthenticationPrincipal User user, CommentViewModel comment) {
        commentService.AddComment(comment, user.getUsername(), user.getId());
        return commentService.GetAllCommentsOnFlowerPage(comment.getFlowerId());
    }

    @PostMapping("/deleteComment")
    public Iterable<Comment> deleteComment(Long commentId, Long flowerId) throws ExecutionException, InterruptedException {
        commentService.DeleteComment(commentId);
        return commentService.GetAllCommentsOnFlowerPage(flowerId);
    }






}

package ru.sinforge.mywebapplication.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Services.CommentService;
import ru.sinforge.mywebapplication.Services.FlowerService;
import ru.sinforge.mywebapplication.Services.UserService;

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
    @GetMapping("/get-collection-flowers")
    public Iterable<Flower> getFlowersCollection() {
        return flowerService.getAllFlowers();

    }
}

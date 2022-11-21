package ru.sinforge.mywebapplication.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sinforge.mywebapplication.Entities.Comment;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.FlowerBouquet;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Services.CommentService;
import ru.sinforge.mywebapplication.Services.FlowerService;
import ru.sinforge.mywebapplication.Services.UserService;
import ru.sinforge.mywebapplication.ViewModels.CommentViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;

@Controller
public class FlowerController {
    private  FlowerService flowerService;
    private  UserService userService;
    private  CommentService commentService;

    FlowerController(FlowerService flowerService, UserService userService, CommentService commentService) {
        this.userService = userService;
        this.flowerService = flowerService;
        this.commentService = commentService;
    }

    public String getFlower(String flower_id) throws ExecutionException, InterruptedException {
        return flowerService.getFlower(flower_id).toString();
    }

    public void IsAuth(User user, Model model) {
        if(user == null) {
            model.addAttribute("user", "NotAuth");
            return;
        }
        model.addAttribute("user", "Auth");
    }

    @GetMapping({"/", "/home"})
    public String getAllFlowers(@AuthenticationPrincipal User user, Model model) {
        IsAuth(user, model);
        Iterable<Flower> flowers = flowerService.getAllFlowers();
        model.addAttribute("FlowerList", flowers);
        return "get_all_flowers";
    }



    @GetMapping("/flower")
    public String FlowerPage(@AuthenticationPrincipal User user, @RequestParam(value = "id", required = true) String id, Model model) throws ExecutionException, InterruptedException {

        if(user != null) {
            model.addAttribute("FlowerInBasket", StreamSupport.stream(
                            userService.GetUserFlowerBasket(user).spliterator(), false)
                    .anyMatch(Flower -> id.equals(Flower.getFlowerId())));
            model.addAttribute("flowerRatingUser", flowerService.getUserRatingOfFlower(id, user.getId()));

        }
        model.addAttribute("flower", flowerService.getFlower(id));
        model.addAttribute("comments", commentService.GetAllCommentsOnFlowerPage(id));
        model.addAttribute("flowerRating", String.format("%.1f", flowerService.getSummaryRating(id)));
        return "flower_page";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/AddToBasket")
    public String AddToBasket(@AuthenticationPrincipal User user, String FlowerId) {
        userService.AddBouquet(user, FlowerId);
        return "redirect:/";

    }

    @PostMapping("/search")
    public String SearchFlower(String FlowerName, int min, int max, Model model) {
        if(FlowerName != "" && min != -1 && max != -1 && min < max) {
            Iterable<Flower> foundedByNameFlowers = flowerService.getFlowerBySearch(FlowerName);
            model.addAttribute("FlowerList", flowerService.SortFlowerByPrice(min, max, foundedByNameFlowers));
        }
        else if(FlowerName == "" && min != -1 && max != -1 && min < max) {
            model.addAttribute("FlowerList", flowerService.SortFlowerByPrice(min, max, flowerService.getAllFlowers()));
        }
        else if (FlowerName != "") {
            model.addAttribute("FlowerList", flowerService.getFlowerBySearch(FlowerName));
        }

        return "get_all_flowers";


    }

    @PreAuthorize("isAuthenticated()")
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




    @PreAuthorize("isAuthenticated()")
    @PostMapping("/review")
    public String SendReview(@AuthenticationPrincipal User user, String flowerId, short rating) {
        flowerService.addReview(user.getId(), flowerId, rating);
        return "redirect:/";
    }


}

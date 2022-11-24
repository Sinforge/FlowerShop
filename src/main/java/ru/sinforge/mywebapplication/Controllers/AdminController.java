package ru.sinforge.mywebapplication.Controllers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.Role;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Services.CommentService;
import ru.sinforge.mywebapplication.Services.FlowerService;
import ru.sinforge.mywebapplication.Services.UserService;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Controller
public class AdminController {
    private FlowerService flowerService;
    private UserService userService;
    private CommentService commentService;


    public AdminController(FlowerService flowerService, UserService userService, CommentService commentService) {
        this.userService = userService;
        this.flowerService = flowerService;
        this.commentService = commentService;
    }

    @PreAuthorize("hasAnyAuthority({'Administrator', 'Moderator'})")
    @GetMapping("/create")
    public String createFlower() {
        return "create_flower";
    }


    @PreAuthorize("hasAnyAuthority({'Administrator', 'Moderator'})")
    @PostMapping("/create")
    public String createFlower(
            @AuthenticationPrincipal User user, Flower flower,
            @RequestParam("img") MultipartFile img, Model model) throws ExecutionException, InterruptedException {
        IsAuth(user, model);
        flowerService.createFlower(flower, img);
        return   "redirect:/";
    }


    public String getFlower(Long flower_id) throws ExecutionException, InterruptedException {
        return flowerService.getFlower(flower_id).toString();
    }

    @PreAuthorize("hasAnyAuthority({'Administrator', 'Moderator'})")
    @PostMapping("/delete")
    public String deleteFlower(Long flower_id) {
        flowerService.deleteFlower(flower_id);
        return "redirect:/";
    }


    @PreAuthorize("hasAnyAuthority({'Administrator', 'Moderator'})")
    @PostMapping("/update")
    public String updateFlower(Flower flower) {
        flowerService.updateFlower(flower);
        return "get_all_flowers";
    }



    @PreAuthorize("hasAuthority('Administrator')")
    @GetMapping("/userlist")
    public String getUserList(Model model) {
            model.addAttribute("UserList", userService.GetUserList());
            String Roles;
            ArrayList<String> RolesCode = new ArrayList<>();
            for (User user: userService.GetUserList()) {
                Roles = "";
                Set<Role> roles = user.getRoles();
                if(roles.contains(Role.Administrator)) {
                    Roles += "1";
                }
                else {
                    Roles += "0";
                }
                if(roles.contains(Role.DefaultUser)) {
                    Roles += "1";
                }
                else {
                    Roles += "0";
                }
                if(roles.contains(Role.Moderator)) {
                    Roles += "1";
                }
                else {
                    Roles += "0";
                }
                RolesCode.add(Roles);

            }
            model.addAttribute("RolesCode", RolesCode);
            return "admin_page";
    }

    @PreAuthorize("hasAuthority('Administrator')")
    @PostMapping("/delete_user")
    public String DeleteUser(Long id) {
        userService.DeleteUserById(id);
        return "redirect:/userlist";
    }




    @PreAuthorize("hasAuthority('Administrator')")
    @PostMapping("/change_user_roles")
    public String ChangeUserRoles(Long id, @RequestParam(value = "roles[]") String[] roles) {
        userService.ChangeUserRoles(roles, id);
        return "redirect:/userlist";
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


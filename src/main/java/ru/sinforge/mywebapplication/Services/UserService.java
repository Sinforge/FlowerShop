package ru.sinforge.mywebapplication.Services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.FlowerBouquet;
import ru.sinforge.mywebapplication.Entities.Role;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Repositories.BasketRepo;
import ru.sinforge.mywebapplication.Repositories.UserRepo;
import ru.sinforge.mywebapplication.ViewModels.UserViewModel;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private FlowerService flowerService;
    private final UserRepo userRepo;
    private final BasketRepo basketRepo;

    @Value("${upload.path.user}")
    private String uploadPath;

    public UserService(FlowerService flowerService, UserRepo userRepo, BasketRepo basketRepo) {
        this.flowerService = flowerService;
        this.userRepo = userRepo;
        this.basketRepo = basketRepo;

    }

    @Transactional
    public void DeleteUserById(Long Id) {
        User user = userRepo.findById(Id).get();
        basketRepo.deleteAllByUser(user);
        userRepo.delete(user)  ;
    }

    public Iterable<FlowerBouquet> GetUserFlowerBasket(User user) {
        return basketRepo.findByUserId(user.getId());
    }

    public void AddBouquet(User user, Long FlowerId) {
        FlowerBouquet flowerBouquet = new FlowerBouquet();
        flowerBouquet.setFlowerId(FlowerId);
        flowerBouquet.setUser(user);
        basketRepo.save(flowerBouquet);
    }

    public User GetUserById(Long id) {
        return userRepo.findById(id).get();
    }

    public void ChangeUserRoles(String[] roles, Long UserId) {
        Set<Role> NewSet = new HashSet<>();
        for (String role: roles) {
            if(role.equals("Administrator")) {
                NewSet.add(Role.Administrator);
            }
            else if(role.equals("DefaultUser")) {
                NewSet.add(Role.DefaultUser);
            }
            else {
                NewSet.add(Role.Moderator);
            }

        }

        User user = userRepo.findById(UserId).get();
        user.setRoles(NewSet);
        userRepo.save(user);
    }

    public Iterable<User> GetUserList() {
        return userRepo.findAll();
    }


    public boolean AddUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        User userFromDB = userRepo.findByUsername(user.getUsername());
        if(userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(Role.Administrator));
        userRepo.save(user);
        return true;
    }

    public void changeData(Long id, UserViewModel user, MultipartFile img) {
        User userFromDB = userRepo.findById(id).get();
        if (userFromDB == null) {
            return;
        }
        if(!img.isEmpty()) {
            try {
                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String ImgName = id + "." + img.getOriginalFilename().split("\\.")[1];
                Path pathImg = Path.of(uploadPath + "/" + ImgName);
                if(Files.exists(pathImg)) {
                    Files.delete(pathImg);
                }
                img.transferTo(new File(uploadPath + "/"+ ImgName));
                userFromDB.setPath(ImgName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(userRepo.findByUsername(user.getUsername()) == null) {
            userFromDB.setUsername(user.getUsername());
        }
        userFromDB.setPassword(userFromDB.getPassword());
        userRepo.save(userFromDB);
    }
    public User getUserByName(String name) {
        return userRepo.findByUsername(name);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}

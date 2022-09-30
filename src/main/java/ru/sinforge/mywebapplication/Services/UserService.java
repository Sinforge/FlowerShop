package ru.sinforge.mywebapplication.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sinforge.mywebapplication.Entities.FlowerBouquet;
import ru.sinforge.mywebapplication.Entities.Role;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Repositories.BasketRepo;
import ru.sinforge.mywebapplication.Repositories.UserRepo;

import java.util.Collections;
@Service
public class UserService implements UserDetailsService {
    private FlowerService flowerService;
    private UserRepo userRepo;
    private BasketRepo basketRepo;

    public UserService(FlowerService flowerService, UserRepo userRepo, BasketRepo basketRepo) {
        this.flowerService = flowerService;
        this.userRepo = userRepo;
        this.basketRepo = basketRepo;

    }


    public Iterable<FlowerBouquet> GetUserFlowerBasket(User user) {
        return basketRepo.findByUserId(user.getId());
    }

    public void AddBouquet(User user, String FlowerId) {
        FlowerBouquet flowerBouquet = new FlowerBouquet();
        flowerBouquet.setFlowerId(FlowerId);
        flowerBouquet.setUser(user);
        basketRepo.save(flowerBouquet);
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}

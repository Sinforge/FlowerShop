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

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

    @Transactional
    public void DeleteUserById(Long Id) {
        User user = userRepo.findById(Id).get();
        basketRepo.deleteAllByUser(user);
        userRepo.delete(user)  ;
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}

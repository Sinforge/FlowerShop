package ru.sinforge.mywebapplication.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sinforge.mywebapplication.Entities.Role;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Repositories.UserRepo;

import java.util.Collections;
@Service
public class UserService implements UserDetailsService {
    private FlowerService flowerService;
    private UserRepo userRepo;

    public UserService(FlowerService flowerService, UserRepo userRepo) {
        this.flowerService = flowerService;
        this.userRepo = userRepo;

    }

    public boolean AddUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        User userFromDB = userRepo.findByUsername(user.getUsername());
        if(userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(Role.DefaultUser));
        userRepo.save(user);
        return true;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}

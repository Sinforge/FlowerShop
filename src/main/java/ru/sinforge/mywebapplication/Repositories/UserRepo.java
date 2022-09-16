package ru.sinforge.mywebapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sinforge.mywebapplication.Entities.User;


@Repository
public interface  UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

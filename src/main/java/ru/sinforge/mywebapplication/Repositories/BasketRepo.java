package ru.sinforge.mywebapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.FlowerBouquet;
import ru.sinforge.mywebapplication.Entities.User;

import java.util.List;

@Repository
public interface BasketRepo extends JpaRepository<FlowerBouquet, Long> {
    List<FlowerBouquet> findByUserId(Long user_id);
    void deleteAllByUser(User user);
}

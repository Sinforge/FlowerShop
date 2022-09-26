package ru.sinforge.mywebapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.FlowerBouquet;
import ru.sinforge.mywebapplication.Entities.User;

import java.util.List;

public interface BasketRepo extends JpaRepository<FlowerBouquet, Long> {
    List<FlowerBouquet> findByUserId(Long user_id);
}

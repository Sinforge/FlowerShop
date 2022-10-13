package ru.sinforge.mywebapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sinforge.mywebapplication.Entities.Rating;

import java.util.ArrayList;

public interface ReviewRepo extends JpaRepository<Rating, Long> {
    ArrayList<Rating> findAllByFlowerid(String flowerid);
    Rating findByFloweridAndUserid(String flowerid, Long userid);
}

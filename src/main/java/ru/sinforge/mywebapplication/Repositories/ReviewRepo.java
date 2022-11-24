package ru.sinforge.mywebapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sinforge.mywebapplication.Entities.Rating;

import java.util.ArrayList;

public interface ReviewRepo extends JpaRepository<Rating, Long> {
    ArrayList<Rating> findAllByFlowerid(Long flowerid);
    Rating findByFloweridAndUserid(Long flowerid, Long userid);
}

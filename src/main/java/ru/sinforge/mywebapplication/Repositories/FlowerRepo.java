package ru.sinforge.mywebapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sinforge.mywebapplication.Entities.Flower;

import java.util.ArrayList;

@Repository
public interface FlowerRepo extends JpaRepository<Flower, Long> {
}

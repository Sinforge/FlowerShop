package ru.sinforge.mywebapplication.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private Long id;


    @Getter
    @Setter
    private short rating;

    @Getter
    @Setter
    private Long flowerid;


    @Getter
    @Setter
    private Long userid;
}

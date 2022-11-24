package ru.sinforge.mywebapplication.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Flower {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Integer price;

    @Getter
    @Setter
    private String imgname;


    @Override
    public String toString() {
        return "Flower{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ImgPath='" + imgname + '\'' +
                '}';
    }


}
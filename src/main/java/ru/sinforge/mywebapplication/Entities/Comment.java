package ru.sinforge.mywebapplication.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String username;


    @Getter
    @Setter
    private Long flowerid;

    @Getter
    @Setter
    private String text;

}

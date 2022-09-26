package ru.sinforge.mywebapplication.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class FlowerBouquet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private long id;
    private String flowerId;
    private int flowerAmount;


    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "flower_bouquet", referencedColumnName = "id")
    @Getter
    @Setter
    private User user;

    public String getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(String flowerId) {
        this.flowerId = flowerId;
    }

    public int getFlowerAmount() {
        return flowerAmount;
    }

    public void setFlowerAmount(int flowerAmount) {
        this.flowerAmount = flowerAmount;
    }
}

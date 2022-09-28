package ru.sinforge.mywebapplication.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.concurrent.Flow;

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

    @Override
    public boolean equals(Object o) {
        boolean Result = false;
        if (o instanceof FlowerBouquet) {
            FlowerBouquet AnotherFlower = (FlowerBouquet) o;
            Result = AnotherFlower.flowerId.equals(this.flowerId);
        }
        return Result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flowerId, flowerAmount, user);
    }
}

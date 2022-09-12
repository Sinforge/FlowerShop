package ru.sinforge.mywebapplication.Entities;



public class Flower {
    private String name;
    private String description;
    private Integer price;
    private String imgpath;


    @Override
    public String toString() {
        return "Flower{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ImgPath='" + imgpath + '\'' +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

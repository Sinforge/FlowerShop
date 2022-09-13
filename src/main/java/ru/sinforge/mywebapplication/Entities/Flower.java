package ru.sinforge.mywebapplication.Entities;



public class Flower {
    private String id;
    private String name;
    private String description;
    private Integer price;
    private String imgname;


    @Override
    public String toString() {
        return "Flower{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ImgPath='" + imgname + '\'' +
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

    public String getImgName() {
        return imgname;
    }

    public void setImgName(String imgname) {
        this.imgname = imgname;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

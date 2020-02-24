package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;

public class Food implements Serializable {

    private String id, image, name, type, weight;
    private int price, quantity;

    public Food() {
    }

    public Food(String id, String image, String name, String type, String weight, int price, int quantity) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;

//these classes refers the data models....these are the columns in db
public class Pet implements Serializable {
    private String id , color , gender , type , name ;

    public Pet() {
    }

    public Pet(String id, String color, String gender, String type, String name) {
        this.id = id;
        this.color = color;
        this.gender = gender;
        this.type = type;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

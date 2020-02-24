package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;

public class Clinic implements Serializable {
    private String address, endTiming, id, image, name, number, startTiming;
    private int fee;

    public Clinic() {
    }

    public Clinic(String address, String endTiming, String id, String image, String name, String number, String startTiming, int fee) {
        this.address = address;
        this.endTiming = endTiming;
        this.id = id;
        this.image = image;
        this.name = name;
        this.number = number;
        this.startTiming = startTiming;
        this.fee = fee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEndTiming() {
        return endTiming;
    }

    public void setEndTiming(String endTiming) {
        this.endTiming = endTiming;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStartTiming() {
        return startTiming;
    }

    public void setStartTiming(String startTiming) {
        this.startTiming = startTiming;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}

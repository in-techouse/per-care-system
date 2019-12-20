package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;

public class Doctor implements Serializable {
    private String id , name , address , phNo , timings ;

    public Doctor() {
    }

    public Doctor(String id, String name, String address, String phNo, String timings) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phNo = phNo;
        this.timings = timings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }
}

package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;

public class Clinic implements Serializable {
   private String id , name , address , timings , phNo ;

    public Clinic() {
    }

    public Clinic(String id, String name, String address, String timings, String phNo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.timings = timings;
        this.phNo = phNo;
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

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }
}

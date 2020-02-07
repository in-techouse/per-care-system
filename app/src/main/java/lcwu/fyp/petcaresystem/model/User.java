package lcwu.fyp.petcaresystem.model;

import android.net.Uri;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName, lastName, email, phNo, id, qualification, image;
    private int role;
    // role == 1, User,
    // role == 2, Doctor,

    public User() {
    }

    public User(String firstName, String lastName, String email, String phNo, String id, String qualification, String image, int role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phNo = phNo;
        this.id = id;
        this.qualification = qualification;
        this.image = image;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

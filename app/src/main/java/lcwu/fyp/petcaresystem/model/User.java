package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName, lastName, email, phNo, id, qualification;
    private int role;
    // role == 1, User,
    // role == 2, Doctor,

    public User() {
    }

    public User(String firstName, String lastName, String email, String phNo, String id, int role, String qualification) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phNo = phNo;
        this.id = id;
        this.role = role;
        this.qualification = qualification;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}

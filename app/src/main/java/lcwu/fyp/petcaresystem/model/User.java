package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName, lastName, email, phNo ;

    public User() {

    }

    public User(String firstName, String lastName, String email, String phNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phNo = phNo;
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
}

package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {
    private String id, userId, firstName, lastName, phoneNumber, email, address, status;
    private int totalItems, totalPrice;
    private HashMap<String, Integer> cartItems;

    public Order() {
    }

    public Order(String id, String userId, String firstName, String lastName, String phoneNumber, String email, String address, String status, int totalItems, int totalPrice, HashMap<String, Integer> cartItems) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.status = status;
        this.totalItems = totalItems;
        this.totalPrice = totalPrice;
        this.cartItems = cartItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public HashMap<String, Integer> getCartItems() {
        return cartItems;
    }

    public void setCartItems(HashMap<String, Integer> cartItems) {
        this.cartItems = cartItems;
    }
}

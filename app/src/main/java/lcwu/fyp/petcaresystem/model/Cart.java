package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;
import java.util.HashMap;

public class Cart implements Serializable {
    private HashMap<String, Integer> cartItems;
    private int totalPrice;

    public Cart() {
        cartItems = new HashMap<>();
        totalPrice = 0;
    }

    public Cart(HashMap<String, Integer> cartItems, int totalPrice) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
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

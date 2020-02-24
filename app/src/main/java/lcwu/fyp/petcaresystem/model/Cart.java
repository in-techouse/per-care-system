package lcwu.fyp.petcaresystem.model;

import java.io.Serializable;
import java.util.HashMap;

public class Cart implements Serializable {
    private HashMap<String, Integer> cartItems;

    public Cart() {
        cartItems = new HashMap<>();
    }

    public Cart(HashMap<String, Integer> cartItems) {
        this.cartItems = cartItems;
    }

    public HashMap<String, Integer> getCartItems() {
        return cartItems;
    }

    public void setCartItems(HashMap<String, Integer> cartItems) {
        this.cartItems = cartItems;
    }
}

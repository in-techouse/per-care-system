package lcwu.fyp.petcaresystem.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.adapters.CartAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Cart;
import lcwu.fyp.petcaresystem.model.CartFood;
import lcwu.fyp.petcaresystem.model.Food;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private Session session;
    private Cart cart;
    private Button checkout;
    private RecyclerView carts;
    private Helpers helpers;
    private LinearLayout loading;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Foods");
    private List<CartFood> cartFoods = new ArrayList<>();
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        session = new Session(getApplicationContext());
        cart = session.getCart();
        if (cart == null) {
            cart = new Cart();
        }

        helpers = new Helpers();

        carts = findViewById(R.id.carts);
        carts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        checkout = findViewById(R.id.checkout);
        loading = findViewById(R.id.loading);
        carts.setVisibility(View.GONE);
        checkout.setVisibility(View.GONE);
        checkout.setOnClickListener(this);
        adapter = new CartAdapter(getApplicationContext());
        carts.setAdapter(adapter);
        loadFoods();
    }

    private void loadFoods() {
        if (!helpers.isConnected(CartActivity.this)) {
            helpers.showError(CartActivity.this, "Internet Error", "No Internet Connection!");
            loading.setVisibility(View.GONE);
            carts.setVisibility(View.VISIBLE);
            checkout.setVisibility(View.VISIBLE);
            return;
        }
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartFoods.clear();
                Log.e("Cart", "Food DataSnapshot: " + dataSnapshot.toString());
                HashMap<String, Integer> cartItems = cart.getCartItems();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.e("Cart", "Food Loop Data: " + d.toString());
                    Food f = d.getValue(Food.class);
                    if (f != null) {
                        Log.e("Cart", "If Food: " + f.getName());
                        if (cartItems.containsKey(f.getId())) {
                            Log.e("Cart", "Cart food is found");
                            for (Map.Entry<String, Integer> stringIntegerEntry : cartItems.entrySet()) {
                                Map.Entry pair = (Map.Entry) stringIntegerEntry;
                                Log.e("FoodOrder", pair.getKey() + " = " + pair.getValue());
                                if (pair.getKey().equals(f.getId())) {
                                    CartFood cartFood = new CartFood(f, (int) pair.getValue());
                                    cartFoods.add(cartFood);
                                }
                            }
                        }
                    }
                }
                Log.e("Cart", "Cart foods list size: " + cartFoods.size());
                adapter.setCartFoods(cartFoods);

                loading.setVisibility(View.GONE);
                carts.setVisibility(View.VISIBLE);
                checkout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                carts.setVisibility(View.VISIBLE);
                checkout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.checkout: {
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return true;
    }
}

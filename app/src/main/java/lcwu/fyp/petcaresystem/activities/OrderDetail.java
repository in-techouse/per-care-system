package lcwu.fyp.petcaresystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

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
import lcwu.fyp.petcaresystem.adapters.OrderFoodAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.CartFood;
import lcwu.fyp.petcaresystem.model.Food;
import lcwu.fyp.petcaresystem.model.Order;
import lcwu.fyp.petcaresystem.model.User;

public class OrderDetail extends AppCompatActivity {
    private Session session;
    private User user;
    private Helpers helpers;
    private Order order;
    private TextView address, name, email, phoneNumber, totalPrice, totalItems;
    private RecyclerView foods;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Foods");
    private List<CartFood> cartFoods = new ArrayList<>();
    private OrderFoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent it = getIntent();
        if (it == null) {
            Log.e("OrderDetail", "Intent is NULL");
            finish();
            return;

        }
        Bundle b = it.getExtras();
        if (b == null) {
            Log.e("OrderDetail", "Extra is NULL");
            finish();
            return;
        }

        order = (Order) b.getSerializable("order");
        if (order == null) {
            Log.e("OrderDetail", "Order is NULL");
            finish();
            return;
        }

        session = new Session(getApplicationContext());
        user = session.getUser();
        helpers = new Helpers();

        address = findViewById(R.id.address);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        totalPrice = findViewById(R.id.totalPrice);
        totalItems = findViewById(R.id.totalItems);
        foods = findViewById(R.id.foods);

        foods.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new OrderFoodAdapter(getApplicationContext());
        foods.setAdapter(adapter);
        address.setText(order.getAddress());
        email.setText(order.getEmail());
        phoneNumber.setText(order.getPhoneNumber());
        totalPrice.setText(order.getTotalPrice() + " RS.");
        totalItems.setText(order.getTotalItems() + "");
        name.setText(order.getFirstName() + " " + order.getLastName());
        loadFoods();
    }

    private void loadFoods() {
        if (!helpers.isConnected(OrderDetail.this)) {
            helpers.showError(OrderDetail.this, "Internet Error", "No Internet Connection!");
            return;
        }
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartFoods.clear();
                Log.e("OrderDetail", "Food DataSnapshot: " + dataSnapshot.toString());
                HashMap<String, Integer> cartItems = order.getCartItems();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.e("OrderDetail", "Food Loop Data: " + d.toString());
                    Food f = d.getValue(Food.class);
                    if (f != null) {
                        Log.e("OrderDetail", "If Food: " + f.getName());
                        if (cartItems.containsKey(f.getId())) {
                            Log.e("OrderDetail", "Cart food is found");
                            for (Map.Entry<String, Integer> stringIntegerEntry : cartItems.entrySet()) {
                                Map.Entry pair = (Map.Entry) stringIntegerEntry;
                                Log.e("OrderDetail", pair.getKey() + " = " + pair.getValue());
                                if (pair.getKey().equals(f.getId())) {
                                    CartFood cartFood = new CartFood(f, (int) pair.getValue());
                                    cartFoods.add(cartFood);
                                }
                            }
                        }
                    }
                }
                Log.e("OrderDetail", "Cart foods list size: " + cartFoods.size());
                adapter.setData(cartFoods);
//                checkout.setText("Total " + total + " RS, Checkout");
//                cart.setTotalPrice(total);
//                session.setCart(cart);
//                loading.setVisibility(View.GONE);
//                carts.setVisibility(View.VISIBLE);
//                checkout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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

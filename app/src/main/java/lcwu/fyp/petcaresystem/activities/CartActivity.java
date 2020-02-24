package lcwu.fyp.petcaresystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.adapters.FoodAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Cart;
import lcwu.fyp.petcaresystem.model.Food;

public class CartActivity extends AppCompatActivity implements View.OnClickListener{

    private Session session;
    private Cart cart;
    private Button checkout;
    private RecyclerView carts;
    private Helpers helpers;
    private LinearLayout loading;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Foods");
    private List<Food> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        session = new Session(getApplicationContext());
        cart = session.getCart();
        if(cart == null){
            cart = new Cart();
        }

        helpers = new Helpers();

        carts = findViewById(R.id.carts);
        checkout = findViewById(R.id.checkout);
        loading = findViewById(R.id.loading);
        carts.setVisibility(View.GONE);
        checkout.setVisibility(View.GONE);
        checkout.setOnClickListener(this);
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
                data.clear();
                Log.e("Cart", "Food DataSnapshot: " + dataSnapshot.toString());
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.e("Cart", "Food Loop Data: " + d.toString());
                    Food f = d.getValue(Food.class);
                    if (f != null) {
                        Log.e("Cart", "If Food: " + f.getName());
                        data.add(f);
                    }
                }

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
        switch (id){
            case R.id.checkout:{
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

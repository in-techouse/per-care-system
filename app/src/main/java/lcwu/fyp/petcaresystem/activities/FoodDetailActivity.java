package lcwu.fyp.petcaresystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Cart;
import lcwu.fyp.petcaresystem.model.Food;

public class FoodDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private CoordinatorLayout parent;
    private Food food;
    private TextView quantity;
    private int finalQuantity = 1;
    private Session session;
    private Cart cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Intent it = getIntent();
        if (it == null) {
            finish();
            return;
        }

        Bundle bundle = it.getExtras();
        if (bundle == null) {
            finish();
            return;
        }

        food = (Food) bundle.getSerializable("food");

        if (food == null) {
            finish();
            return;
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView image = findViewById(R.id.image);
        parent = findViewById(R.id.parent);

        Glide.with(getApplicationContext()).load(food.getImage()).into(image);

        TextView name = findViewById(R.id.name);
        TextView type = findViewById(R.id.type);
        TextView price = findViewById(R.id.price);
        TextView weight = findViewById(R.id.weight);

        name.setText(food.getName());
        type.setText(food.getType());
        price.setText(food.getPrice() + " RS");
        weight.setText(food.getWeight());
        quantity = findViewById(R.id.quantity);
        ImageView minusCart = findViewById(R.id.minusCart);
        ImageView plusCart = findViewById(R.id.plusCart);
        Button addToCart = findViewById(R.id.addToCart);

        minusCart.setOnClickListener(this);
        plusCart.setOnClickListener(this);
        addToCart.setOnClickListener(this);


        session = new Session(getApplicationContext());
        cart = session.getCart();
        if (cart == null) {
            cart = new Cart();
        } else {
            HashMap<String, Integer> cartItems = cart.getCartItems();
            for (Map.Entry<String, Integer> stringIntegerEntry : cartItems.entrySet()) {
                Map.Entry pair = (Map.Entry) stringIntegerEntry;
                if (pair.getKey().equals(food.getId())) {
                    finalQuantity = (int) pair.getValue();
                    break;
                }
                Log.e("FoodOrder", pair.getKey() + " = " + pair.getValue());
            }
        }

        quantity.setText(finalQuantity + "");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.minusCart: {
                if (finalQuantity == 1)
                    return;
                finalQuantity--;
                quantity.setText(finalQuantity + "");
                break;
            }
            case R.id.plusCart: {
                if (finalQuantity == food.getQuantity())
                    return;
                finalQuantity++;
                quantity.setText(finalQuantity + "");

                break;
            }
            case R.id.addToCart: {
                Log.e("FoodOrder", "Add to Cart button clicked");
                cart = session.getCart();
                if (cart == null)
                    cart = new Cart();
                cart.getCartItems().put(food.getId(), finalQuantity);
                session.setCart(cart);

                HashMap<String, Integer> cartItems = cart.getCartItems();
                for (Map.Entry<String, Integer> stringIntegerEntry : cartItems.entrySet()) {
                    Map.Entry pair = (Map.Entry) stringIntegerEntry;
                    Log.e("FoodOrder", pair.getKey() + " = " + pair.getValue());
                }
                Snackbar.make(parent, food.getName() + " added to cart successfully.", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
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

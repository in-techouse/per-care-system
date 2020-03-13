package lcwu.fyp.petcaresystem.activities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Cart;
import lcwu.fyp.petcaresystem.model.Order;
import lcwu.fyp.petcaresystem.model.User;

public class PlaceOrder extends AppCompatActivity implements View.OnClickListener {
    private Session session;
    private User user;
    private Helpers helpers;
    private EditText firstName, lastName, email, phoneNumber, address;
    private String strFirstName, strLastName, strEmail, strPhoneNumber, strAddress;
    private Button placeOrder;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders");
    private LinearLayout loading;
    private ScrollView main;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        session = new Session(PlaceOrder.this);
        user = session.getUser();
        helpers = new Helpers();
        cart = session.getCart();
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        TextView totalItems = findViewById(R.id.totalItems);
        TextView totalPrice = findViewById(R.id.totalPrice);

        loading = findViewById(R.id.loading);
        main = findViewById(R.id.main);

        placeOrder = findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(this);

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhNo());
        totalItems.setText(cart.getCartItems().size() + "");
        totalPrice.setText(cart.getTotalPrice() + " RS.");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.placeOrder: {
                if (!helpers.isConnected(PlaceOrder.this)) {
                    helpers.showError(PlaceOrder.this, "ERROR!", "No internet connection found.\nConnect to a network and try again.");
                    return;
                }

                if (isValid()) {
                    Order order = new Order();
                    placeOrder.setVisibility(View.GONE);
                    main.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    order.setId(reference.push().getKey());
                    order.setUserId(user.getId());
                    order.setFirstName(strFirstName);
                    order.setLastName(strLastName);
                    order.setEmail(strEmail);
                    order.setPhoneNumber(strPhoneNumber);
                    order.setAddress(strAddress);
                    order.setTotalItems(cart.getCartItems().size());
                    order.setTotalPrice(cart.getTotalPrice());
                    order.setCartItems(cart.getCartItems());
                    reference.child(order.getId()).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            placeOrder.setVisibility(View.VISIBLE);
                            main.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            session.destroyCart();
                            helpers.showSuccessAndFinish(PlaceOrder.this, "SUCCESS", "Your order has been placed successfully.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            placeOrder.setVisibility(View.VISIBLE);
                            main.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            helpers.showError(PlaceOrder.this, "ERROR!", "Something went wrong.\nPlease try again layer.");
                        }
                    });
                }
                break;
            }
        }
    }

    private boolean isValid() {
        boolean flag = true;
        strFirstName = firstName.getText().toString();
        strLastName = lastName.getText().toString();
        strEmail = email.getText().toString();
        strPhoneNumber = phoneNumber.getText().toString();
        strAddress = address.getText().toString();

        if (strFirstName.length() < 3) {
            firstName.setError("Enter a valid name.");
            flag = false;
        } else {
            firstName.setError(null);
        }

        if (strLastName.length() < 3) {
            lastName.setError("Enter a valid name.");
            flag = false;
        } else {
            lastName.setError(null);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            email.setError("Enter a valid email.");
            flag = false;
        } else {
            email.setError(null);
        }

        if (strPhoneNumber.length() != 11) {
            phoneNumber.setError("Enter a valid phone number.");
            flag = false;
        } else {
            phoneNumber.setError(null);
        }
        if (strAddress.length() < 13) {
            address.setError("Enter a valid address.");
            flag = false;
        } else {
            address.setError(null);
        }

        return flag;
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

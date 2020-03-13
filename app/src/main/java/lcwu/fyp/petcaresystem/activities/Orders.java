package lcwu.fyp.petcaresystem.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
import java.util.Collections;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.adapters.OrderAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Order;
import lcwu.fyp.petcaresystem.model.User;

public class Orders extends AppCompatActivity {

    private LinearLayout loading;
    private TextView noRecord;
    private RecyclerView orders;
    private User user;
    private Helpers helpers;
    private List<Order> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders");
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        loading = findViewById(R.id.loading);
        noRecord = findViewById(R.id.noRecord);
        orders = findViewById(R.id.orders);
        Session session = new Session(Orders.this);
        user = session.getUser();
        helpers = new Helpers();
        data = new ArrayList<>();

        orders.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new OrderAdapter(getApplicationContext());
        orders.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        if (!helpers.isConnected(getApplicationContext())) {
            helpers.showError(Orders.this, "ERROR!", "No internet connection found.\n\bConnect to a network and try again.");
            return;
        }

        loading.setVisibility(View.VISIBLE);
        noRecord.setVisibility(View.GONE);
        orders.setVisibility(View.GONE);
        reference.orderByChild("userId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reference.removeEventListener(this);
                data.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Order order = d.getValue(Order.class);
                    if (order != null) {
                        data.add(order);
                    }
                }

                Collections.reverse(data);

                if (data.size() > 0) {
                    adapter.setData(data);
                    noRecord.setVisibility(View.GONE);
                    orders.setVisibility(View.VISIBLE);
                } else {
                    noRecord.setVisibility(View.VISIBLE);
                    orders.setVisibility(View.GONE);
                }
                loading.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                reference.removeEventListener(this);
                loading.setVisibility(View.GONE);
                noRecord.setVisibility(View.VISIBLE);
                orders.setVisibility(View.GONE);
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

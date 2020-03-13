package lcwu.fyp.petcaresystem.activities;

import android.os.Bundle;
import android.util.Log;
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
import lcwu.fyp.petcaresystem.adapters.NotificationAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Notification;
import lcwu.fyp.petcaresystem.model.User;

public class Notifications extends AppCompatActivity {

    private LinearLayout loading;
    private TextView noRecord;
    private RecyclerView notifications;
    private Helpers helpers;
    private User user;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notifications");
    private List<Notification> data;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        loading = findViewById(R.id.loading);
        noRecord = findViewById(R.id.noRecord);
        notifications = findViewById(R.id.notifications);
        helpers = new Helpers();
        Session session = new Session(getApplicationContext());
        user = session.getUser();
        data = new ArrayList<>();
        adapter = new NotificationAdapter(user.getRole());
        notifications.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        notifications.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        if (!helpers.isConnected(getApplicationContext())) {
            helpers.showError(Notifications.this, "ERROR!", "No internet connection found.\nConnect to a network and try again.");
            return;
        }

        loading.setVisibility(View.VISIBLE);
        noRecord.setVisibility(View.GONE);
        notifications.setVisibility(View.GONE);
        reference.orderByChild("userId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                Log.e("Notifications", "Food DataSnapshot: " + dataSnapshot.toString());
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.e("Notifications", "Food Loop Data: " + d.toString());
                    Notification n = d.getValue(Notification.class);
                    if (n != null) {
                        Log.e("Notifications", "If Food: " + n.getId());
                        data.add(n);
                    }
                }
                Collections.reverse(data);
                if (data.size() > 0) {
                    adapter.setData(data);
                    notifications.setVisibility(View.VISIBLE);
                    noRecord.setVisibility(View.GONE);
                } else {
                    notifications.setVisibility(View.GONE);
                    noRecord.setVisibility(View.VISIBLE);
                }
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                noRecord.setVisibility(View.VISIBLE);
                notifications.setVisibility(View.GONE);
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

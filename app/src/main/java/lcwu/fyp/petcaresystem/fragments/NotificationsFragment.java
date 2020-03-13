package lcwu.fyp.petcaresystem.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private LinearLayout loading;
    private TextView noRecord;
    private RecyclerView notifications;
    private Helpers helpers;
    private User user;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notifications");
    private List<Notification> data;
    private NotificationAdapter adapter;


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        loading = root.findViewById(R.id.loading);
        noRecord = root.findViewById(R.id.noRecord);
        notifications = root.findViewById(R.id.notifications);
        helpers = new Helpers();
        Session session = new Session(getActivity());
        user = session.getUser();
        data = new ArrayList<>();
        adapter = new NotificationAdapter(user.getRole());
        notifications.setLayoutManager(new LinearLayoutManager(getActivity()));
        notifications.setAdapter(adapter);
        loadData();
        return root;
    }

    private void loadData() {
        if (!helpers.isConnected(getActivity())) {
            helpers.showError(getActivity(), "ERROR!", "No internet connection found.\nConnect to a network and try again.");
            return;
        }

        loading.setVisibility(View.VISIBLE);
        noRecord.setVisibility(View.GONE);
        notifications.setVisibility(View.GONE);
        reference.orderByChild("doctorId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
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

}

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
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.adapters.AppointmentsAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Appointment;
import lcwu.fyp.petcaresystem.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentsFragment extends Fragment {

    private LinearLayout loading;
    private TextView noRecord;
    private RecyclerView appointments;
    private Helpers helpers;
    private User user;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Appointments");
    private List<Appointment> data;
    private AppointmentsAdapter adapter;

    public AppointmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_appointments, container, false);
        loading = root.findViewById(R.id.loading);
        noRecord = root.findViewById(R.id.noRecord);
        appointments = root.findViewById(R.id.appointments);

        helpers = new Helpers();
        Session session = new Session(getActivity());
        user = session.getUser();
        data = new ArrayList<>();
        appointments.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AppointmentsAdapter(getActivity());
        appointments.setAdapter(adapter);
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
        appointments.setVisibility(View.GONE);
        reference.orderByChild("doctorId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                Log.e("Appointments", "Appointment DataSnapshot: " + dataSnapshot.toString());
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.e("Appointments", "Appointment Loop Data: " + d.toString());
                    Appointment a = d.getValue(Appointment.class);
                    if (a != null) {
                        Log.e("Appointments", "If Appointment: " + a.getAddress());
                        data.add(a);
                    }
                }
                if (data.size() > 0) {
                    adapter.setData(data);
                    appointments.setVisibility(View.VISIBLE);
                    noRecord.setVisibility(View.GONE);
                } else {
                    appointments.setVisibility(View.GONE);
                    noRecord.setVisibility(View.VISIBLE);
                }
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                noRecord.setVisibility(View.VISIBLE);
                appointments.setVisibility(View.GONE);
            }
        });
    }

}

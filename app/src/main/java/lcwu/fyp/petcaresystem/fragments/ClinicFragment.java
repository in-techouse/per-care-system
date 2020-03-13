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
import lcwu.fyp.petcaresystem.adapters.ClinicAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Clinic;
import lcwu.fyp.petcaresystem.model.User;

public class ClinicFragment extends Fragment {
    private LinearLayout loading;
    private TextView noClinic;
    private RecyclerView clinics;
    private Helpers helpers;
    private List<Clinic> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Clinics");
    private ClinicAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_clinic, container, false);


        loading = v.findViewById(R.id.Loading);
        noClinic = v.findViewById(R.id.noClinic);
        clinics = v.findViewById(R.id.clinics);
        adapter = new ClinicAdapter(getActivity());
        clinics.setLayoutManager(new LinearLayoutManager(getActivity()));
        clinics.setAdapter(adapter);
        Session session = new Session(getActivity());
        User user = session.getUser();
        helpers = new Helpers();
        data = new ArrayList<>();
        loadClinics();
        return v;
    }

    private void loadClinics() {
        if (!helpers.isConnected(getActivity())) {
            helpers.showError(getActivity(), "Internet Error", "No Internet Connection!");
            return;
        }

        loading.setVisibility(View.VISIBLE);
        noClinic.setVisibility(View.GONE);
        clinics.setVisibility(View.GONE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("clinic data", "in data change");
                data.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Clinic c = d.getValue(Clinic.class);
                    Log.e("clinic data", "got clinic obj");
                    if (c != null) {
                        Log.e("clinic data", "value being added");
                        data.add(c);
                    }
                }
                if (data.size() > 0) {
                    adapter.setData(data);
                    clinics.setVisibility(View.VISIBLE);
                    noClinic.setVisibility(View.GONE);
                } else {
                    clinics.setVisibility(View.GONE);
                    noClinic.setVisibility(View.VISIBLE);
                }
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                noClinic.setVisibility(View.VISIBLE);
                clinics.setVisibility(View.GONE);
            }
        });
    }
}

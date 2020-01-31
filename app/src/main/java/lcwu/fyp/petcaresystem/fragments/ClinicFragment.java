package lcwu.fyp.petcaresystem.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Clinic;
import lcwu.fyp.petcaresystem.model.Doctor;
import lcwu.fyp.petcaresystem.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClinicFragment extends Fragment {



    private LinearLayout loading;
    private TextView noClinic;
    private RecyclerView clinics;
    private Session session;
    private User user;
    private Helpers helpers;
    private List<Clinic> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Clinics");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_clinic, container, false);


        loading = v.findViewById(R.id.Loading);
        noClinic = v.findViewById(R.id.noClinic);
        clinics = v.findViewById(R.id.clinics);
        session = new Session(getActivity());
        user = session.getUser();
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
        reference.orderByChild("userId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Clinic c = d.getValue(Clinic.class);
                    if (c != null) {
                        data.add(c);
                    }
                }
                if (data.size()>0){
                    clinics.setVisibility(View.VISIBLE);
                    noClinic.setVisibility(View.GONE);
                }
                else{
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

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
import lcwu.fyp.petcaresystem.model.Doctor;
import lcwu.fyp.petcaresystem.model.Order;
import lcwu.fyp.petcaresystem.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment {


    private LinearLayout loading;
    private TextView noDoctor;
    private RecyclerView doctors;
    private Session session;
    private User user;
    private Helpers helpers;
    private List<Doctor> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doctor, container, false);

        loading = v.findViewById(R.id.Loading);
        noDoctor = v.findViewById(R.id.noDoctor);
        doctors = v.findViewById(R.id.doctors);
        session = new Session(getActivity());
        user = session.getUser();
        helpers = new Helpers();
        data = new ArrayList<>();
        loadDoctors();

        return v;
    }

    private void loadDoctors() {
        if (!helpers.isConnected(getActivity())) {
            helpers.showError(getActivity(), "Internet Error", "No Internet Connection!");
            return;
        }

        loading.setVisibility(View.VISIBLE);
        noDoctor.setVisibility(View.GONE);
        doctors.setVisibility(View.GONE);
        reference.orderByChild("userId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                noDoctor.setVisibility(View.VISIBLE);
                doctors.setVisibility(View.GONE);

            }


        });


    }
}

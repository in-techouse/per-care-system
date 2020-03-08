package lcwu.fyp.petcaresystem.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import lcwu.fyp.petcaresystem.adapters.ClinicAdapter;
import lcwu.fyp.petcaresystem.adapters.DoctorAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
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
    private List<User> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
    private DoctorAdapter adapter;

    public static DoctorFragment newInstance() {
        DoctorFragment myFragment = new DoctorFragment();

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doctor, container, false);

        loading = v.findViewById(R.id.Loading);
        noDoctor = v.findViewById(R.id.noDoctor);
        doctors = v.findViewById(R.id.doctors);
        adapter = new DoctorAdapter(getActivity());
        doctors.setLayoutManager(new LinearLayoutManager(getActivity()));
        doctors.setAdapter(adapter);
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
        reference.orderByChild("role").equalTo(2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("doctor", "in data Change");
                data.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    User doc = d.getValue(User.class);
                    Log.e("doctor", "Traversing");
                    if (doc != null) {
                        Log.e("doctor", "got doc obj");
                        data.add(doc);
                    }
                }
                if (data.size() > 0) {
                    Log.e("doctor", "data size " + data.size());
                    adapter.setData(data);
                    doctors.setVisibility(View.VISIBLE);
                    noDoctor.setVisibility(View.GONE);
                } else {
                    doctors.setVisibility(View.GONE);
                    noDoctor.setVisibility(View.VISIBLE);
                }
                loading.setVisibility(View.GONE);
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

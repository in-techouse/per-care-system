package lcwu.fyp.petcaresystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import lcwu.fyp.petcaresystem.adapters.AppointmentsAdapter;
import lcwu.fyp.petcaresystem.adapters.ClinicAdapter;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Appointment;
import lcwu.fyp.petcaresystem.model.Clinic;
import lcwu.fyp.petcaresystem.model.User;

public class Appointments extends AppCompatActivity {

    private LinearLayout appointmentsLoading;
    private TextView noAppointments;
    private RecyclerView appointments;
    private Session session;
    private User user;
    private Helpers helpers;
    private List<Appointment> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Appointments");
    private Appointment appointment;
    private AppointmentsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        appointmentsLoading = findViewById(R.id.appointmentsLoading);
        noAppointments = findViewById(R.id.noAppointments);
        appointments = findViewById(R.id.appointments);
        session = new Session(getApplicationContext());
        user = session.getUser();
        helpers = new Helpers();
        data = new ArrayList<>();
        adapter = new AppointmentsAdapter(Appointments.this);
        appointments.setLayoutManager(new LinearLayoutManager(Appointments.this));
       appointments.setAdapter(adapter);

        loadAppointments();
    }

    void loadAppointments(){
        Log.e("appointments" , "in load Appointments");
        if (!helpers.isConnected(getApplicationContext())) {
            helpers.showError(Appointments.this, "Internet Error", "No Internet Connection!");
            return;
        }

        appointmentsLoading.setVisibility(View.VISIBLE);
        noAppointments.setVisibility(View.GONE);
        appointments.setVisibility(View.GONE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.e("appointments" , "in load Appointments");

                    appointment = snapshot.getValue(Appointment.class);
                    if(appointment != null){
                        if(appointment.getPatientId().equals(user.getId())){
                            Log.e("appointments" , "Got my Appointment");
                            data.add(appointment);

                        }

                    }
                }
                if(data.size() >0){
                    Log.e("appointments" , "Data Passed to adapter");
                    adapter.setData(data);
                    appointmentsLoading.setVisibility(View.GONE);
                    appointments.setVisibility(View.VISIBLE);


                }
                else {
                    appointmentsLoading.setVisibility(View.GONE);
                    noAppointments.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

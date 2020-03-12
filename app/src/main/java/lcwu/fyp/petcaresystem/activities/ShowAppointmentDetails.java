package lcwu.fyp.petcaresystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Appointment;
import lcwu.fyp.petcaresystem.model.User;

public class ShowAppointmentDetails extends AppCompatActivity {

    private Appointment appointment;
    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users");
    private TextView detailDate, detailTime, detailCategory, detailDName, detailAddress, detailStatus, detailD_Phone, docQualification;
    User doctorDetails, patientDetails;
    CircleImageView detailImage;
    RelativeLayout appointmentDetailsProgress;
    LinearLayout mainDetails;
    User user;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appointment_details);

        Intent it = getIntent();
        if (it == null) {
            Log.e("Appointment details", "Intent is NULL");
            finish();
            return;

        }
        Bundle b = it.getExtras();
        if (b == null) {
            Log.e("Appointment details", "Extra is NULL");
            finish();
            return;
        }

        appointment = (Appointment) b.getSerializable("appointment");
        if (appointment == null) {
            Log.e("Appointment details", "Booking is NULL");
            finish();
            return;
        } else {
            Log.e("Fix Appointment", "got the Appointment  details");
        }
        detailDate = findViewById(R.id.detailDate);
        detailTime = findViewById(R.id.detailTime);
        detailCategory = findViewById(R.id.detailCategory);
        detailDName = findViewById(R.id.detailDName);
        detailAddress = findViewById(R.id.detailAddress);
        detailStatus = findViewById(R.id.detailStatus);
        detailD_Phone = findViewById(R.id.detailD_Phone);
        appointmentDetailsProgress = findViewById(R.id.appointmentDetailsProgress);
        mainDetails = findViewById(R.id.mainDetails);
        detailImage = findViewById(R.id.detailImage);
        docQualification = findViewById(R.id.docQualification);
        session = new Session(ShowAppointmentDetails.this);
        user = session.getUser();

        getDetails();


    }

    void getDetails() {

        appointmentDetailsProgress.setVisibility(View.VISIBLE);


        userReference.child(appointment.getDoctorId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("details", "received values are " + dataSnapshot);
                doctorDetails = dataSnapshot.getValue(User.class);
                Log.e("details", "received values are " + doctorDetails.getFirstName());

                Log.e("details", "received object " + dataSnapshot);
                if (doctorDetails.getImage() != null && doctorDetails.getImage().length() > 0) {
                    Log.e("adapter", "image added");
                    Glide.with(ShowAppointmentDetails.this).load(doctorDetails.getImage()).into(detailImage);
                } else {
                    Log.e("adapter", "Image not  found");
                }
                appointmentDetailsProgress.setVisibility(View.GONE);
                mainDetails.setVisibility(View.VISIBLE);
                patientDetails = dataSnapshot.getValue(User.class);
                Log.e("details", "received object " + patientDetails.getFirstName());
                detailDate.setText(appointment.getDate());
                detailTime.setText(appointment.getTime());
                detailCategory.setText(appointment.getCategory());
                detailAddress.setText(appointment.getAddress());
                detailStatus.setText(appointment.getStatus());
                detailD_Phone.setText(doctorDetails.getPhNo());
                detailDName.setText(doctorDetails.getFirstName() + " " + doctorDetails.getLastName());
                docQualification.setText(doctorDetails.getQualification());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

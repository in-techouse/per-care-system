package lcwu.fyp.petcaresystem.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Appointment;
import lcwu.fyp.petcaresystem.model.Notification;
import lcwu.fyp.petcaresystem.model.User;

public class ShowAppointmentDetails extends AppCompatActivity implements View.OnClickListener {

    private Appointment appointment;
    private DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users");
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Appointments");
    private DatabaseReference notificationReference = FirebaseDatabase.getInstance().getReference().child("Notifications");
    private TextView detailDate, detailTime, detailCategory, detailDName, detailAddress, detailStatus, detailD_Phone, docQualification;
    private User doctorDetails, user;
    private CircleImageView detailImage;
    private RelativeLayout appointmentDetailsProgress;
    private LinearLayout mainDetails;
    private Helpers helpers;
    private ProgressDialog loadingBar;


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
        View bottomView = findViewById(R.id.bottomView);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Button accept = findViewById(R.id.accept);
        Button reject = findViewById(R.id.reject);
        accept.setOnClickListener(this);
        reject.setOnClickListener(this);
        Session session = new Session(ShowAppointmentDetails.this);
        user = session.getUser();
        loadingBar = new ProgressDialog(this);
        helpers = new Helpers();

        if (user.getRole() == 1) {
            bottomView.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.GONE);
        } else if (!appointment.getStatus().equals("Requested")) {
            bottomView.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.GONE);
        }
        getDetails();


    }

    void getDetails() {

        appointmentDetailsProgress.setVisibility(View.VISIBLE);
        String id = appointment.getPatientId();
        if (user.getRole() == 1) {
            id = appointment.getDoctorId();
        }

        userReference.child(id).addValueEventListener(new ValueEventListener() {
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
                detailDate.setText(appointment.getDate());
                detailTime.setText(appointment.getTime());
                detailCategory.setText(appointment.getCategory());
                detailAddress.setText(appointment.getAddress());
                detailStatus.setText(appointment.getStatus());
                detailD_Phone.setText(doctorDetails.getPhNo());
                detailDName.setText(doctorDetails.getFirstName() + " " + doctorDetails.getLastName());
                if (user.getRole() == 1)
                    docQualification.setText(doctorDetails.getQualification());
                else
                    docQualification.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.accept: {
                loadingBar.setTitle("ACCEPT APPOINTMENT REQUEST");
                loadingBar.setMessage("Please wait, while we are marking this request accepted...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                appointment.setStatus("Accepted");
                saveAppointment();
                break;
            }
            case R.id.reject: {
                loadingBar.setTitle("REJECT APPOINTMENT REQUEST");
                loadingBar.setMessage("Please wait, while we are marking this request rejected...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                appointment.setStatus("Rejected");
                saveAppointment();
                break;
            }
        }
    }

    private void saveAppointment() {
        reference.child(appointment.getId()).setValue(appointment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sendNotification();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                helpers.showError(ShowAppointmentDetails.this, "ERROR!", "Something went wrong.\nPlease try again later.");
            }
        });
    }

    private void sendNotification() {
        Notification notification = new Notification();
        notification.setId(notificationReference.push().getKey());
        notification.setDoctorId(user.getId());
        notification.setUserId(doctorDetails.getId());
        if (appointment.getStatus().equals("Rejected")) {
            notification.setUserMessage("Your appointment request with " + user.getFirstName() + " " + user.getLastName() + " has been rejected.");
            notification.setDoctorMessage("You rejected the appointment request of " + doctorDetails.getFirstName() + " " + doctorDetails.getLastName());
        } else if (appointment.getStatus().equals("Accepted")) {
            notification.setUserMessage("Your appointment request with " + user.getFirstName() + " " + user.getLastName() + " has been accepted.");
            notification.setDoctorMessage("You accepted the appointment request of " + doctorDetails.getFirstName() + " " + doctorDetails.getLastName());
        }

        notification.setAppointmentId(appointment.getId());
        notificationReference.child(notification.getId()).setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loadingBar.dismiss();
                helpers.showSuccessAndFinish(ShowAppointmentDetails.this, "APPOINTMENT UPDATED!", "Your appointment has been updated successfully.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                helpers.showSuccessAndFinish(ShowAppointmentDetails.this, "APPOINTMENT UPDATED!", "Your appointment has been updated successfully.");
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

package lcwu.fyp.petcaresystem.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Appointment;
import lcwu.fyp.petcaresystem.model.User;

public class FixAppointment extends AppCompatActivity {

    private User doc, user;
    private TextView timeText;
    private TextView saveTime;
    private TextView savedTime;
    private TextView savedDate;
    private Spinner petCategory;
    private int hour;
    private int min;
    private String strCategory;
    private String strTime;
    private String strDate;
    private String strAddress;
    final Calendar myCalendar = Calendar.getInstance();
    private DatabaseReference appointmentReference = FirebaseDatabase.getInstance().getReference().child("Appointments");
    private Helpers helpers;
    private EditText address;
    private LinearLayout main;
    private RelativeLayout loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_appointment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.e("Fix Appointment", "Going to check index");
        Intent it = getIntent();
        if (it == null) {
            Log.e("Fix Appointment", "Intent is NULL");
            finish();
            return;

        }
        Bundle b = it.getExtras();
        if (b == null) {
            Log.e("Fix Appointment", "Extra is NULL");
            finish();
            return;
        }

        doc = (User) b.getSerializable("doc");
        if (doc == null) {
            Log.e("Fix Appointment", "Booking is NULL");
            finish();
            return;
        } else {
            Log.e("Fix Appointment", "got the doc");
        }

        ImageView doc_image = findViewById(R.id.doc_image);
        TextView doc_name = findViewById(R.id.docName);
        TextView docContact = findViewById(R.id.docContact);
        TextView doc_detail = findViewById(R.id.docDetail);
        saveTime = findViewById(R.id.saveTime);
        timeText = findViewById(R.id.timeText);
        savedTime = findViewById(R.id.savedTime);
        savedDate = findViewById(R.id.savedDate);
        petCategory = findViewById(R.id.petCategory);
        Button getAppointmentBtn = findViewById(R.id.getAppointmentBtn);
        address = findViewById(R.id.address);
        loading = findViewById(R.id.loading);
        main = findViewById(R.id.main);

        Session session = new Session(FixAppointment.this);
        user = session.getUser();
        helpers = new Helpers();

        getAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!helpers.isConnected(FixAppointment.this)) {
                    helpers.showError(FixAppointment.this, "ERROR!", "No internet connection found.\nPlease connect to a network and try again.");
                    return;
                }
                if (isValid()) {
                    fixAppointment();
                }
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        savedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FixAppointment.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        savedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                min = myCalendar.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(FixAppointment.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                savedTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        if (doc.getImage() != null && doc.getImage().length() > 0) {
            Log.e("fix Appointment", "image added");
            Glide.with(this).load(doc.getImage()).into(doc_image);
        } else {
            Log.e("adapter", "Image not  found");
        }

        doc_name.setText(doc.getFirstName() + " " + doc.getLastName());
        doc_detail.setText(doc.getQualification());
        docContact.setText(doc.getPhNo());
        saveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTime.setVisibility(View.VISIBLE);
                Log.e("time", "received time" + hour);
                savedTime.setVisibility(View.VISIBLE);
                savedTime.setText("" + hour + " : " + min);
                saveTime.setVisibility(View.GONE);
                showTime(hour, min);
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        savedDate.setText(sdf.format(myCalendar.getTime()));
    }


    public void showTime(int hour, int min) {
        String format;
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        savedTime.setText(new StringBuilder().append(hour).append(" : ").append(min).append(" ").append(format));
    }

    private boolean isValid() {
        boolean flag = true;
        strTime = savedTime.getText().toString();
        strDate = savedDate.getText().toString();
        strCategory = petCategory.getSelectedItem().toString();
        strAddress = address.getText().toString();
        String error = "";
        if (strTime.equals("Select Time")) {
            error = error + "Specify to your time.\n";
            flag = false;
        }

        if (strDate.equals("Select Date")) {
            error = error + "Specify to your date.\n";
            flag = false;
        }

        if (petCategory.getSelectedItemPosition() == 0) {
            error = error + "Select your pet category.\n";
            flag = false;
        }

        if (strAddress.length() < 6) {
            address.setError("Enter a valid address.");
            flag = false;
        }

        if (error.length() > 1) {
            helpers.showError(FixAppointment.this, "ERROR!", error);
        }
        return flag;
    }

    private void fixAppointment() {
        loading.setVisibility(View.VISIBLE);
        main.setVisibility(View.GONE);
        Appointment appointment = new Appointment();
        String id = appointmentReference.push().getKey();
        appointment.setId(id);
        appointment.setTime(strTime);
        appointment.setDate(strDate);
        appointment.setAddress(strAddress);
        appointment.setCategory(strCategory);
        appointment.setPatientId(user.getId());
        appointment.setDoctorId(doc.getId());
        appointment.setStatus("Requested");
        appointmentReference.child(appointment.getId()).setValue(appointment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("appointment", "in On Success");
                loading.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);
                helpers.showSuccessAndFinish(FixAppointment.this, "", "Your request for appointment with " + doc.getFirstName() + " " + doc.getLastName() + " has been sent.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("appointment", "in on Failure");
                loading.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);
                helpers.showError(FixAppointment.this, "ERROR!", "Something went wrong.\nPlease try again later.");

            }
        });

    }
}


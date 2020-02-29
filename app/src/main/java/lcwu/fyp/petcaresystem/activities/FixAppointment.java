package lcwu.fyp.petcaresystem.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.Appointment;
import lcwu.fyp.petcaresystem.model.User;

public class FixAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private User doc;
    private ImageView doc_image;
    private TextView doc_name , doc_detail ,timeText , saveTime ,savedTime, savedDate;
    private TimePicker appointment_time;
    private Spinner petCategory;
    private static final String[] paths = {"Cat", "Dog", "Big Dog"};
    private int hour;
    private int min;
    private String format, strCategory = "" , strTime , strDate ;
    final Calendar myCalendar = Calendar.getInstance();
    private Button getAppointmentBtn , fixAppointmentBtn;
    private LinearLayout appointmentForm;
    private User user;
    private Session session;
    private RelativeLayout loadingPanel;
    DatabaseReference appointmentReference  = FirebaseDatabase.getInstance().getReference().child("Appointments");



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        }else {
            Log.e("Fix Appointment" , "got the doc");
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action"+doc, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        doc_image = findViewById(R.id.doc_image);
        doc_name = findViewById(R.id.docName);
        doc_detail = findViewById(R.id.docDetail);
        appointment_time = findViewById(R.id.appointment_time);
        saveTime = findViewById(R.id.saveTime);
        timeText = findViewById(R.id.timeText);
        savedTime = findViewById(R.id.savedTime);
        savedDate = findViewById(R.id.savedDate);
        petCategory = findViewById(R.id.petCategory);
        getAppointmentBtn = findViewById(R.id.getAppointmentBtn);
        appointmentForm = findViewById(R.id.appointmentForm);
        fixAppointmentBtn = findViewById(R.id.fixAppointmentBtn);
        loadingPanel = findViewById(R.id.loadingPanel);

        session = new Session(FixAppointment.this);
        user = session.getUser();

        getAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAppointmentBtn.setVisibility(View.GONE);
                appointmentForm.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FixAppointment.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petCategory.setAdapter(adapter);
        petCategory.setOnItemSelectedListener(this);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
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
                new DatePickerDialog(FixAppointment.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                savedTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, min, false);
                timePickerDialog.show();


//                appointment_time.setVisibility(View.VISIBLE);
//                saveTime.setVisibility(View.GONE);
//                saveTime.setVisibility(View.VISIBLE);
            }
        });
        if (doc.getImage() != null && doc.getImage().length() > 0) {
            Log.e("fix Appointment", "image added");
            Glide.with(this).load(doc.getImage()).into(doc_image);
        }else {
            Log.e("adapter", "Image not  found");
        }

        doc_name.setText(doc.getFirstName()+" "+doc.getLastName() );
        doc_detail.setText(doc.getQualification());
        saveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointment_time.setVisibility(View.GONE);
                saveTime.setVisibility(View.VISIBLE);
                Log.e("time" , "received time"+hour);
                savedTime.setVisibility(View.VISIBLE);
                savedTime.setText(""+hour+" : "+min);
                saveTime.setVisibility(View.GONE);
                showTime(hour , min);

            }
        });

        fixAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPanel.setVisibility(View.VISIBLE);
                fixAppointment();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        savedDate.setText(sdf.format(myCalendar.getTime()));
    }


    public void showTime(int hour, int min) {
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

        savedTime.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                Log.e("spinner" , "pos 0");
                strCategory = "Cat";
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                strCategory = "Dog";
                Log.e("spinner" , "pos 1");

                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                strCategory = "Big Dog";
                Log.e("spinner" , "pos 2");

                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    void fixAppointment(){
        Appointment appointment = new Appointment();
        String id  = appointmentReference.push().getKey();
        appointment.setId(id);
        appointment.setPatientId(user.getId());
        appointment.setDoctorId(doc.getId());
        appointment.setCategory(strCategory);
        appointment.setTime(savedTime.getText().toString());
        appointment.setDate(savedDate.getText().toString());
        appointment.setStatus("Requested");
        appointmentReference.child(appointment.getId()).setValue(appointment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("appointment" , "in On Success");
                loadingPanel.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("appointment" , "in on Failure");
                loadingPanel.setVisibility(View.GONE);
            }
        });

    }
}


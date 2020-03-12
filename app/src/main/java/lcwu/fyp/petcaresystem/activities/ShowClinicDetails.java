package lcwu.fyp.petcaresystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.model.Clinic;

public class ShowClinicDetails extends AppCompatActivity {

    private Clinic clinic;
    private ImageView clinic_image;
    private TextView clinic_name, start_time, end_time, clinic_address, clinic_fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clinic_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        clinic = (Clinic) b.getSerializable("clinic");
        if (clinic == null) {
            Log.e("Fix Appointment", "Booking is NULL");
            finish();
            return;
        } else {
            Log.e("Fix Appointment", "got the doc");
        }


        clinic_image = findViewById(R.id.clinic_image);
        clinic_name = findViewById(R.id.clinic_name);
        start_time = findViewById(R.id.startTiming);
        end_time = findViewById(R.id.endTiming);
        clinic_fee = findViewById(R.id.fee);
        clinic_address = findViewById(R.id.address);
        if (clinic.getImage() != null && clinic.getImage().length() > 0) {
            Log.e("fix Appointment", "image added");
            Glide.with(this).load(clinic.getImage()).into(clinic_image);
        } else {
            Log.e("adapter", "Image not  found");
        }

        clinic_name.setText(clinic.getName());
        start_time.setText(clinic.getStartTiming());
        end_time.setText(clinic.getEndTiming());
        clinic_address.setText(clinic.getAddress());
        clinic_fee.setText(clinic.getFee() + " Rs");

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

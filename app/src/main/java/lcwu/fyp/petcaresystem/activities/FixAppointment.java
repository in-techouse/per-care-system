package lcwu.fyp.petcaresystem.activities;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.model.User;

public class FixAppointment extends AppCompatActivity {

    private User doc;
    private ImageView doc_image;
    private TextView doc_name , doc_detail;

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
        if (doc.getImage() != null && doc.getImage().length() > 0) {
            Log.e("fix Appointment", "image added");
            Glide.with(this).load(doc.getImage()).into(doc_image);
        }else {
            Log.e("adapter", "Image not  found");
        }

        doc_name.setText(doc.getFirstName()+" "+doc.getLastName() );
        doc_detail.setText(doc.getQualification());

    }
}

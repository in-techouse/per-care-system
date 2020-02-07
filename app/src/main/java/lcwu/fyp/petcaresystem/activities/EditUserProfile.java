package lcwu.fyp.petcaresystem.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.io.File;
import java.util.Calendar;
import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.User;

public class EditUserProfile extends AppCompatActivity implements View.OnClickListener{
    private final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private String strFirstName, strLastName, strEmail, strPhone = "";

    private User user;
    private Session session;
    private Helpers helpers;
    private ImageView imageView;
    private EditText firstName , lastName , email , phone;
    private Button editSubmitBtn;
    private DatabaseReference databaseReference;
    private boolean isImage;
    private Uri imagePath;
    private ProgressBar registrationProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        isImage = false;
//        imagePath = ;
        FloatingActionButton fab = findViewById(R.id.galleryBtn);
        fab.setOnClickListener(this);

        helpers = new Helpers();
        session = new Session(EditUserProfile.this);
        user = session.getUser();

        toolbar.setTitle(user.getFirstName());
        setSupportActionBar(toolbar);
        imageView =  findViewById(R.id.userImage);
        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        phone = findViewById(R.id.editphone);
        email = findViewById(R.id.editEmail);
        editSubmitBtn = findViewById(R.id.editSubmitBtn);
        registrationProgress= findViewById(R.id.registrationProgress);


        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        phone.setText(user.getPhNo());
        if(user.getImage() != null && !user.getImage().equalsIgnoreCase("")){
            Glide.with(getApplicationContext()).load(user.getImage()).into(imageView);
        }
        else {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.profile));
        }

        editSubmitBtn.setOnClickListener(this);
    }

    private boolean isValid()
    {
        boolean flag= true;
        if (strFirstName.length()<3) {
            firstName.setError("Enter a valid Name");
            flag= false;
        }
        else {
            firstName.setError(null);
        }


        if (strLastName.length()<3) {
            lastName.setError("Enter a valid Name");
            flag= false;
        }
        else {
            lastName.setError(null);
        }


        if (strPhone.length() != 11) {
            phone.setError("Enter a valid Mobile Number");
            flag= false;
        }
        else {
            phone.setError(null);
        }

        return flag;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.editSubmitBtn:{
                Log.e("Profile", "Button Clicked");
                //check internet
                boolean isConn = helpers.isConnected(EditUserProfile.this);
                if (!isConn)
                {
                    helpers.showError(EditUserProfile.this , "Internet Connection Error" ,"Not Connected To Internet! Check Your Connection And Try Again" );
                    return;
                }
                Log.e("Profile", "Internent Connected");
                strFirstName = firstName.getText().toString();
                strLastName = lastName.getText().toString();
                strEmail = email.getText().toString();
                strPhone = phone.getText().toString();
                boolean flag = isValid();
                Log.e("Profile", "Validation Done");
                if(flag){
                    Log.e("Profile", "Validation Successful");
                    if(isImage){
                        Log.e("Profile", "Image Found");
                        uploadImage();
                    }
                    else{
                        Log.e("Profile", "No Image Found");
                        saveToDatabase();
                    }
                }
                break;
            }
            case R.id.galleryBtn:{
                boolean flag = hasPermissions(EditUserProfile.this, PERMISSIONS);
                if(!flag){
                    ActivityCompat.requestPermissions(EditUserProfile.this, PERMISSIONS, 1);
                }
                else{
                    openGallery();
                }
                break;
            }
        }
    }
    private void uploadImage(){
        registrationProgress.setVisibility(View.VISIBLE);
        editSubmitBtn.setVisibility(View.GONE);
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Users").child(user.getId());
        Uri selectedMediaUri = Uri.parse(imagePath.toString());

        File file = new File(selectedMediaUri.getPath());
         Log.e("file" , "in file object value "+file.toString());
        Log.e("Profile", "Uri: " + selectedMediaUri.getPath() + " File: " + file.exists());
//        if(!file.exists()){
//            Log.e("Uri" , "file not exists showing error");
//            registrationProgress.setVisibility(View.GONE);
//            editSubmitBtn.setVisibility(View.VISIBLE);
//            helpers.showError(EditUserProfile.this, "ERROR!", "Something went wrong.\n Please try again later.");
//            return;
//        }
        Calendar calendar = Calendar.getInstance();

        storageReference.child(calendar.getTimeInMillis()+"").putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("Profile" , "in OnSuccess "+uri.toString());
                        user.setImage(uri.toString());
                        registrationProgress.setVisibility(View.GONE);
                        editSubmitBtn.setVisibility(View.VISIBLE);
                        saveToDatabase();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Profile", "Downlaod Url: " + e.getMessage());
                        registrationProgress.setVisibility(View.GONE);
                        editSubmitBtn.setVisibility(View.VISIBLE);
                        helpers.showError(EditUserProfile.this, "ERROR!", "Something went wrong.\n Please try again later.");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Profile", "Upload Image Url: " + e.getMessage());
                registrationProgress.setVisibility(View.GONE);
                editSubmitBtn.setVisibility(View.VISIBLE);
                helpers.showError(EditUserProfile.this, "ERROR!", "Something went wrong.\n Please try again later.");            }
        });
    }

    private void saveToDatabase(){
        registrationProgress.setVisibility(View.VISIBLE);
        editSubmitBtn.setVisibility(View.GONE);
        user.setEmail(strEmail);
        user.setFirstName(strFirstName);
        user.setLastName(strLastName);
        user.setPhNo(strPhone);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getId());
        databaseReference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                profileSuccessDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registrationProgress.setVisibility(View.GONE);
                editSubmitBtn.setVisibility(View.VISIBLE);
                helpers.showError(EditUserProfile.this, "ERROR!", "Something went wrong.\n Please try again later.");
            }
        });
    }

    private void profileSuccessDialog(){
        registrationProgress.setVisibility(View.GONE);
        editSubmitBtn.setVisibility(View.VISIBLE);
        session.setSession(user);

        MaterialDialog mDialog = new MaterialDialog.Builder(EditUserProfile.this)
                .setTitle("PROFILE UPDATED")
                .setMessage("Your Profile has been updated successfully.")
                .setCancelable(false)
                .setPositiveButton("OK", R.drawable.ic_action_name, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(EditUserProfile.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        // Delete Operation
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_action_close, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(EditUserProfile.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Profile", "Gallery Call Back Received in Fragment with Request Code: " + requestCode);
        if (requestCode == 2) {
            if(resultCode == RESULT_OK){
                if(data != null){
                    Uri image = data.getData();
                    if(image != null){
                        Glide.with(getApplicationContext()).load(image).into(imageView);
                        imagePath = image;
                        isImage = true;
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            openGallery();
        }
    }
    private boolean hasPermissions(Context c, String... permission){
        for(String p : permission){
            if(ActivityCompat.checkSelfPermission(c, p) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
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

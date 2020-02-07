package lcwu.fyp.petcaresystem.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
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
import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.User;

public class EditUserProfile extends AppCompatActivity implements View.OnClickListener{
    private final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private String strFirstName , strLastName , strEmail , strPhone = "";

    private User user;
    private Session session;
    private Helpers helpers;
    private ImageView imageView;
    private EditText firstName , lastName , email , phone;
    private Button editSubmitBtn;
    private DatabaseReference databaseReference;
    private boolean isImage;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        isImage = false;
        imagePath = "";
        FloatingActionButton fab = findViewById(R.id.galleryBtn);
        fab.setOnClickListener(this);

        helpers = new Helpers();
        session = new Session(EditUserProfile.this);
        user = session.getUser();

        imageView =  findViewById(R.id.userImage);
        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        phone = findViewById(R.id.editphone);
        email = findViewById(R.id.editEmail);
        editSubmitBtn = findViewById(R.id.editSubmitBtn);

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
        editSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                      strFirstName = firstName.getText().toString();
//                        strLastName = lastName.getText().toString();
//                        strEmail = email.getText().toString();
//                        strPhone = phone.getText().toString();
//
//                        //check internet
////                        isConn = helpers.isConnected(EditUserProfile.this);
////                        if (!isConn)
////                        {
////                            helpers.showError(EditUserProfile.this , "Internet Connection Error" ,"Not Connected To Internet! Check Your Connection And Try Again" );
////                            return;
////                        }
//
//
//                        boolean flag = isValid();
//                        if (flag) {
//
//                            editedUser.setFirstName(strFirstName);
//                            editedUser.setLastName(strLastName);
//                            editedUser.setEmail(strEmail);
//                            editedUser.setPhNo(strPhone);
//                            String Id= strEmail.replace("@" , "-");
//                            Id = Id.replace("." , "_");
//                            editedUser.setId(Id);
//                            editedUser.setQualification("");
//                            editedUser.setRole(1);
//
//                            if(imageStatus){
//                                saveImage(imageView);
//                            }
//                            //firebase
//
////                    registrationProgress.setVisibility(View.VISIBLE);
//                            editSubmitBtn.setVisibility(View.GONE);
//
//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//                            reference.child("Users").child(Id).setValue(editedUser).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.e("firebase" , "in OnSuccess");
//                                    session.setSession(editedUser);
//                                    Intent intent = new Intent(EditUserProfile.this , Dashboard.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.e("firebase" , "in OnFailure");
////                                    registrationProgress.setVisibility(View.GONE);
//                                            editSubmitBtn.setVisibility(View.VISIBLE);
//                                            helpers.showError(EditUserProfile.this , "Profile Updation  Failed!" , e.getMessage());
//                                        }
//                                    });
//
//
//                        }
//                    //go to next activity
            }
        });

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
                        imagePath = image.toString();
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

package lcwu.fyp.petcaresystem.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.airbnb.lottie.utils.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.User;

public class EditUserProfile extends AppCompatActivity {

    String strFirstName , strLastName , strEmail , strPhone = "";

    User user = new User();
    Session session;
    Helpers helpers;
    ImageView imageView;
    EditText firstName , lastName , email , phone;
    Button editSubmitBtn;
    boolean isConn;
    boolean imageStatus;
    final User editedUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageStatus = false;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.galleryBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);

//                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(pickPhoto , 1);


//To Just Open the Gallery
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
//                        "content://media/internal/images/media"));
//                startActivity(intent);//Open Gallery here
            }
        });
        imageView =  findViewById(R.id.userImage);
        imageView.setImageResource(R.drawable.profile);
        session = new Session(EditUserProfile.this);
        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        phone = findViewById(R.id.editphone);
        email = findViewById(R.id.editEmail);
        editSubmitBtn = findViewById(R.id.editSubmitBtn);
        User sessionUser = session.getUser();
        firstName.setText(sessionUser.getFirstName());
        lastName.setText(sessionUser.getLastName());
        email.setText(sessionUser.getEmail());
        phone.setText(sessionUser.getPhNo());
//        editSubmitBtn.setOnClickListener(this);
        editSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      strFirstName = firstName.getText().toString();
                        strLastName = lastName.getText().toString();
                        strEmail = email.getText().toString();
                        strPhone = phone.getText().toString();

                        //check internet
//                        isConn = helpers.isConnected(EditUserProfile.this);
//                        if (!isConn)
//                        {
//                            helpers.showError(EditUserProfile.this , "Internet Connection Error" ,"Not Connected To Internet! Check Your Connection And Try Again" );
//                            return;
//                        }


                        boolean flag = isValid();
                        if (flag) {

                            editedUser.setFirstName(strFirstName);
                            editedUser.setLastName(strLastName);
                            editedUser.setEmail(strEmail);
                            editedUser.setPhNo(strPhone);
                            String Id= strEmail.replace("@" , "-");
                            Id = Id.replace("." , "_");
                            editedUser.setId(Id);
                            editedUser.setQualification("");
                            editedUser.setRole(1);

                            if(imageStatus){
                                saveImage(imageView);
                            }
                            //firebase

//                    registrationProgress.setVisibility(View.VISIBLE);
                            editSubmitBtn.setVisibility(View.GONE);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                            reference.child("Users").child(Id).setValue(editedUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.e("firebase" , "in OnSuccess");
                                    session.setSession(editedUser);
                                    Intent intent = new Intent(EditUserProfile.this , Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("firebase" , "in OnFailure");
//                                    registrationProgress.setVisibility(View.GONE);
                                            editSubmitBtn.setVisibility(View.VISIBLE);
                                            helpers.showError(EditUserProfile.this , "Profile Updation  Failed!" , e.getMessage());
                                        }
                                    });


                        }
                    //go to next activity
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Log.e("User image" , "Before setImageBitmap");
            imageView.setImageURI(data.getData());
            imageStatus = true;
        }
    }

    public void saveImage(ImageView imageView){
        if (imageView != null){
            Log.e("image" , "image exists");
        }


        Uri u = Uri.parse(imageView.toString());
        Log.e("image" , "URI "+u);
        final StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("Users").child(session.getUser().getId()).child("Images");
        imageRef.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getMetadata() != null){
                    if(taskSnapshot.getMetadata().getReference() != null){
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //
                                Log.e("image" , "in image OnSuccess");
                                editedUser.setImageUri(uri);
                                Log.e("image" , "image URL added");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("image" , "in failure");
                            }
                        });
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });
    }

}

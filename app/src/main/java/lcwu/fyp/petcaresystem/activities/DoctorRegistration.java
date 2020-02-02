package lcwu.fyp.petcaresystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.User;

public class DoctorRegistration extends AppCompatActivity implements View.OnClickListener{

    Button btnSubmit;
    TextView go_To_Login;
    EditText edtFirstName, edtLastName, edtEmail, edtPhone, edtPassword, edtCnfrmPass,qualification;
    String str1stName, strLastName, strEmail, strPh, strPass, strCnfmPass,strqualification;
    ProgressBar registrationProgress;
    Helpers helpers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);


        helpers = new Helpers();

        btnSubmit=findViewById(R.id.btnSubmit);
        go_To_Login=findViewById(R.id.go_To_Login);

        edtFirstName= findViewById(R.id.edtFirstName);
        edtLastName= findViewById(R.id.edtLastName);
        edtEmail= findViewById(R.id.edtEmail);
        edtPhone= findViewById(R.id.edtphone);
        edtPassword= findViewById(R.id.edtpassword);
        edtCnfrmPass= findViewById(R.id.edtCnfrmPass);
        registrationProgress= findViewById(R.id.registrationProgress);
        qualification = findViewById(R.id.qualification);

        btnSubmit.setOnClickListener(this);
        go_To_Login.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        int id=v.getId();
        switch (id)
        {
            case R.id.btnSubmit: {
                str1stName = edtFirstName.getText().toString();
                strLastName = edtLastName.getText().toString();
                strEmail = edtEmail.getText().toString();
                strPh = edtPhone.getText().toString();
                strPass = edtPassword.getText().toString();
                strCnfmPass = edtCnfrmPass.getText().toString();
                strqualification = qualification.getText().getClass().toString();

                //check internet
                boolean isConn = helpers.isConnected(DoctorRegistration.this);
                if (!isConn)
                {
                    helpers.showError(DoctorRegistration.this , "Internet Connection Error" ,"Not Connected To Internet! Check Your Connection And Try Again" );
                    return;
                }


                boolean flag = isValid();
                if (flag) {

                    //firebase

                    registrationProgress.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.GONE);


                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(strEmail, strPass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                                public void onSuccess(AuthResult authResult) {

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();       //ths line is for db reference; and it is always compulsory for editing db
                                    final User user = new User();
                                    user.setFirstName(str1stName);
                                    user.setLastName(strLastName);
                                    user.setEmail(strEmail);
                                    user.setPhNo(strPh);
                                    user.setQualification(strqualification);
                                    user.setRole(2);

                                    String id= strEmail.replace("@" , "-");
                                    id = id.replace("." , "_");
                                    user.setId(id);


                                    reference.child("Users").child(id).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Session session = new Session(DoctorRegistration.this);
                                            session.setSession(user);

                                            //start dashboard activity
                                            Intent intent = new Intent(DoctorRegistration.this , Dashboard.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            registrationProgress.setVisibility(View.GONE);
                                            btnSubmit.setVisibility(View.VISIBLE);
                                            helpers.showError(DoctorRegistration.this , "Registration Failed!" , e.getMessage());
                                        }

                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            registrationProgress.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            helpers.showError(DoctorRegistration.this , "Registration Failed!" , e.getMessage());

                        }
                    });

                }
                break;
            }
            //go to next activity

            case R.id.go_To_Login:
            {
                Intent it= new Intent(DoctorRegistration.this, LoginActivity.class);
                startActivity(it);
                break;
            }

        }
    }


    private boolean isValid()
    {
        boolean flag= true;
        if (str1stName.length()<3) {
            edtFirstName.setError("Enter a valid Name");
            flag= false;
        }
        else {
            edtFirstName.setError(null);
        }



        if (strLastName.length()<3) {
            edtLastName.setError("Enter a valid Name");
            flag= false;
        }
        else {
            edtLastName.setError(null);
        }


        if (strEmail.length()<6 || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            edtEmail.setError("Enter a valid Email");
            flag= false;
        }
        else {
            edtEmail.setError(null);
        }

        if (strqualification.length()<3) {
            qualification.setError("Enter a valid Email");
            flag= false;
        }
        else {
            qualification.setError(null);
        }


        if (strPh.length() != 11) {
            edtPhone.setError("Enter a valid Mobile Number");
            flag= false;
        }
        else {
            edtPhone.setError(null);
        }


        if (strPass.length()<6)
        {
            edtPassword.setError("Enter a valid Password");
            flag= false;
        }
        else
        {
            edtPassword.setError(null);
        }


        if (strCnfmPass.length()<6)
        {
            edtCnfrmPass.setError("Password does not Match");
            flag= false;
        }
        else
        {
            edtCnfrmPass.setError(null);
        }
        return flag;
    }





}



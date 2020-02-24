package lcwu.fyp.petcaresystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;
import lcwu.fyp.petcaresystem.director.Session;
import lcwu.fyp.petcaresystem.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText edtEmail, edtPassword;
    private String strEmail;
    private String strPassword;
    private ProgressBar LoginProgress;
    private Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        TextView go_To_Signup = findViewById(R.id.go_To_Signup);
        TextView forgotPassword = findViewById(R.id.FPassword);
        LoginProgress = findViewById(R.id.LoginProgress);
        TextView go_To_doctor_reg = findViewById(R.id.go_To_doctor_reg);

        btnLogin.setOnClickListener(this);
        go_To_Signup.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        go_To_doctor_reg.setOnClickListener(this);
        helpers = new Helpers();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnLogin: {
                boolean flag1 = helpers.isConnected(LoginActivity.this);
                if (!flag1) {
                    helpers.showError(LoginActivity.this, "Internet Connection Error", "Not Connected To Internet! Check Your Connection And Try Again");
                    return;
                }


                strEmail = edtEmail.getText().toString();

                strPassword = edtPassword.getText().toString();

                boolean flag = isValid();
                if (flag) {
                    ///firebase
                    LoginProgress.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.GONE);

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(strEmail, strPassword)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();  //for database read,write ,delete and update
                                    String id = strEmail.replace("@", "-");
                                    id = id.replace(".", "_");

                                    reference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Log.e("Login", dataSnapshot.toString());
                                            LoginProgress.setVisibility(View.GONE);
                                            btnLogin.setVisibility(View.VISIBLE);
                                            if (dataSnapshot.getValue() != null) {
                                                Log.e("Login", dataSnapshot.getValue().toString());
                                                //data is valid
                                                User u = dataSnapshot.getValue(User.class);
                                                if(u != null) {

                                                    Session session = new Session(LoginActivity.this);
                                                    session.setSession(u);
                                                    //start dashboard activity
                                                    if(u.getRole() == 1){
                                                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    else if(u.getRole() == 2){
                                                        Intent intent = new Intent(LoginActivity.this, DoctorDashboard.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    else{
                                                        helpers.showError(LoginActivity.this, "LOGIN FAILED!", "It looks like that yoo're not authorized to login here.");
                                                    }
                                                }
                                                else{
                                                    Log.e("login", "in inner else");
                                                    helpers.showError(LoginActivity.this, "LOGIN FAILED!", "Something went wrong.\nPlease try again later.");
                                                }
                                            } else {
                                                Log.e("login", "in inner else");
                                                helpers.showError(LoginActivity.this, "LOGIN FAILED!", "Something went wrong.\nPlease try again later.");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            LoginProgress.setVisibility(View.GONE);
                                            btnLogin.setVisibility(View.VISIBLE);
                                            Log.e("login", "on Canceleld");
                                            helpers.showError(LoginActivity.this, "LOGIN FAILED!", "Something went wrong.\nPlease try again later.");

                                        }
                                    });
                                    //use capital letters and always plural
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            LoginProgress.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);
                            Log.e("login", "in failure listerner");
                            helpers.showError(LoginActivity.this, "LOGIN FAILED!", e.getMessage());
                        }
                    });

                }

                break;
            }
            case R.id.go_To_Signup: {
                Intent it = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(it);
                break;
            }
            case R.id.FPassword: {
                Intent it = new Intent(LoginActivity.this, FPasswordActivity.class);
                startActivity(it);
                break;
            }
            case R.id.go_To_doctor_reg: {
                Intent it = new Intent(LoginActivity.this, DoctorRegistration.class);
                startActivity(it);
                break;
            }

        }

    }

    private boolean isValid() {
        boolean Flag = true;

        if (strEmail.length() < 6 || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            edtEmail.setError("Enter a valid Email");
            Flag = false;
        } else {
            edtEmail.setError(null);
        }

        if (strPassword.length() < 6) {
            edtPassword.setError("Enter a valid Password");
            Flag = false;
        } else {
            edtPassword.setError(null);
        }
        return Flag;
    }

}

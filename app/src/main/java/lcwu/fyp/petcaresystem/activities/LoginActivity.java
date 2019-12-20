package lcwu.fyp.petcaresystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText edtEmail,edtPassword;
    String strEmail;
    String strPassword;
    TextView go_To_Signup;
    TextView ForgotPassword;
    ProgressBar LoginProgress;
    Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        go_To_Signup = findViewById(R.id.go_To_Signup);
        ForgotPassword = findViewById(R.id.FPassword);
        LoginProgress = findViewById(R.id.LoginProgress);

        btnLogin.setOnClickListener(this);
        go_To_Signup.setOnClickListener(this);
        ForgotPassword.setOnClickListener(this);

        helpers = new Helpers();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btnLogin:{
                boolean flag1 = helpers.isConnected(LoginActivity.this);
                if(!flag1){


                    MaterialDialog mDialog = new MaterialDialog.Builder(LoginActivity.this)
                            .setTitle("Internet Connection Error")
                            .setMessage("Not Connected To Internet! Check Your Connection And Try Again")
                            .setCancelable(false)
                            .setPositiveButton("OK", R.drawable.ic_action_name, new MaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                   dialogInterface.dismiss();
                                    // Delete Operation
                                }
                            })
                            .setNegativeButton("Cancel", R.drawable.ic_action_close, new MaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .build();

                    // Show Dialog
                    mDialog.show();
                    return;
                }


                strEmail = edtEmail.getText().toString();

                strPassword = edtPassword.getText().toString();


                boolean flag = isValid();
                if (flag)
                {
                    ///firebase
                    LoginProgress.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.GONE);

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(strEmail, strPassword)
                      .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                          @Override
                          public void onSuccess(AuthResult authResult) {
                              LoginProgress.setVisibility(View.GONE);
                              btnLogin.setVisibility(View.VISIBLE);

                              Log.e("LogIn", "Success");
                              Intent it = new Intent(LoginActivity.this, Dashboard.class);
                              startActivity(it);
                              finish();
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            LoginProgress.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);
                            Log.e("LogIn", "Fail " + e.getMessage());
                            MaterialDialog mDialog = new MaterialDialog.Builder(LoginActivity.this)
                                    .setTitle("Login Failed!")
                                    .setMessage(e.getMessage())
                                    .setCancelable(false)
                                    .setPositiveButton("OK", R.drawable.ic_action_name, new MaterialDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dialogInterface.dismiss();
                                            // Delete Operation
                                        }
                                    })
                                    .setNegativeButton("Cancel", R.drawable.ic_action_close, new MaterialDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .build();

                            // Show Dialog
                            mDialog.show();
                        }
                    });


                }

                break;

            }
            case R.id.go_To_Signup:{
                Intent it = new Intent( LoginActivity.this, RegistrationActivity.class);
                startActivity(it);
                break;
            }
            case R.id.FPassword:{
                Intent it = new Intent( LoginActivity.this, FPasswordActivity.class);
                startActivity(it);
                break;
            }

        }

    }

    private boolean isValid(){
        boolean Flag = true;

        if (strEmail.length()<6 || ! Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()){
            edtEmail.setError("Enter A Valid Email");
            Flag = false;
        }
        else {
            edtEmail.setError(null);
        }

        if (strPassword.length()<6){
            edtPassword.setError("Enter A Valid Password");
            Flag = false;
        }
        else {
            edtPassword.setError(null);
        }
        return Flag;
    }


}

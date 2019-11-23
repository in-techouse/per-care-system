package lcwu.fyp.petcaresystem;

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

import java.sql.Ref;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText edtEmail,edtPassword;
    String strEmail;
    String strPassword;
    TextView go_To_Signup;
    ProgressBar LoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        go_To_Signup = findViewById(R.id.go_To_Signup);
        LoginProgress = findViewById(R.id.LoginProgress);

        btnLogin.setOnClickListener(this);
        go_To_Signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btnLogin:{
                strEmail = edtEmail.getText().toString();

                strPassword = edtPassword.getText().toString();

                boolean flag = isValid();
                if (flag){
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



                          }
                      }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            LoginProgress.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);
                            Log.e("LogIn", "Fail " + e.getMessage());
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

        }

    }

    private boolean isValid(){
        boolean Flag = true;

        if (strEmail.length()<6 || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()){
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

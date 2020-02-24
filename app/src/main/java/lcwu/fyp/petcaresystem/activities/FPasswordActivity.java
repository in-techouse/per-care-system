package lcwu.fyp.petcaresystem.activities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Helpers;

public class FPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnContinue;
    private EditText edtconfirm;
    private String strconfirm;
    private ProgressBar LoginProgress;
    private Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpassword);

        btnContinue = findViewById(R.id.btnContinue);
        edtconfirm = findViewById(R.id.edtEmail);
        LoginProgress = findViewById(R.id.LoginProgress);


        btnContinue.setOnClickListener(this);

        helpers = new Helpers();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnContinue: {

                if (!helpers.isConnected(getApplicationContext())) {
                    helpers.showError(FPasswordActivity.this, "ERROR!", "No internet connection found.\n Connect to a network and try again.");
                    return;
                }

                strconfirm = edtconfirm.getText().toString();
                boolean flag = isValid();
                if (flag) {
                    ///Firebase
                    LoginProgress.setVisibility(View.VISIBLE);
                    btnContinue.setVisibility(View.GONE);

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(strconfirm)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    LoginProgress.setVisibility(View.GONE);
                                    btnContinue.setVisibility(View.VISIBLE);
                                    helpers.showError(FPasswordActivity.this, "EMAIL SENT!", "A password recovery email has been sent to your account.\nPlease check the email and follow the instructions to change your password.");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            LoginProgress.setVisibility(View.GONE);
                            btnContinue.setVisibility(View.VISIBLE);
                            helpers.showError(FPasswordActivity.this, "ERROR!", e.getMessage());
                        }
                    });
                }
                break;
            }

            case R.id.go_To_Login: {
                finish();
                break;
            }
        }
    }

    private boolean isValid() {
        boolean flag = true;

        if (strconfirm.length() < 6 || !Patterns.EMAIL_ADDRESS.matcher(strconfirm).matches()) {
            edtconfirm.setError("Enter a valid Email");
            flag = false;
        } else {
            edtconfirm.setError(null);
        }

        return flag;
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

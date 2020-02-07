package lcwu.fyp.petcaresystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import lcwu.fyp.petcaresystem.R;

public class FPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnContinue;
    EditText edtconfirm;
    String strconfirm;
    ProgressBar LoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpassword);

        btnContinue = findViewById(R.id.btnContinue);
        edtconfirm = findViewById(R.id.edtconfirm);
        LoginProgress = findViewById(R.id.LoginProgress);


        btnContinue.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnContinue:{

                  strconfirm = edtconfirm.getText().toString();
                  boolean flag = isValid();
                  if(flag){
                      ///Firebase
                      LoginProgress.setVisibility(View.VISIBLE);
                      btnContinue.setVisibility(View.GONE);

                      FirebaseAuth auth = FirebaseAuth.getInstance();
                      auth.sendPasswordResetEmail(strconfirm)
                              .addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {

                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {

                                  }
                              });
                  }
                  break;
            }

            case R.id.go_To_Login:
            {
                Intent it= new Intent(FPasswordActivity.this, LoginActivity.class);
                startActivity(it);
                break;
            }
        }
    }

    private boolean isValid(){
        boolean Flag = true;

        if (strconfirm.length()<6){
            edtconfirm.setError("Enter A Valid Password");
            Flag = false;
        }
        else {
            edtconfirm.setError(null);
        }
        return Flag;
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

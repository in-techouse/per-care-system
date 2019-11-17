package lcwu.fyp.petcaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

      Button btnSubmit;
      TextView go_To_Login;
      EditText edtFirstName, edtLastName, edtEmail, edtPhone, edtPassword, edtCnfrmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

       btnSubmit=findViewById(R.id.btnSubmit);
       go_To_Login=findViewById(R.id.go_To_Login);

      edtFirstName= findViewById(R.id.edtFirstName);
      edtLastName= findViewById(R.id.edtLastName);
      edtEmail= findViewById(R.id.edtEmail);
      edtPhone= findViewById(R.id.edtphone);
      edtPassword= findViewById(R.id.edtpassword);
      edtCnfrmPass= findViewById(R.id.edtCnfrmPass);


       btnSubmit.setOnClickListener(this);
       go_To_Login.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

      int id=v.getId();
      switch (id)
        {
            case R.id.btnSubmit:
            {
              String str1stName= edtFirstName.getText().toString();
              String strLastName= edtLastName.getText().toString();
              String strEmail= edtEmail.getText().toString();
              String strPh= edtPhone.getText().toString();
              String strpass= edtPassword.getText().toString();
              String strcnfmPass= edtCnfrmPass.getText().toString();

              if (str1stName.length()<3)
              {
                 edtFirstName.setError("Enter a valid Name");
              }
              else
              {
                  edtFirstName.setError(null);
              }



              if (strLastName.length()<3)
              {
                  edtLastName.setError("Enter a valid Name");
              }
              else
              {
                  edtLastName.setError(null);
              }


              if (strEmail.length()<6 || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())
              {
                   edtEmail.setError("Enter a valid Email");
              }
              else
              {
                  edtEmail.setError(null);
              }


              if (strPh.length() != 11)
              {
                 edtPhone.setError("Enter a valid Mobile Number");
              }
              else
              {
                  edtPhone.setError(null);
              }


              if (strpass.length()<6)
              {
                  edtPassword.setError("Enter a valid Password");
              }
              else
              {
                  edtPassword.setError(null);
              }


              if (strcnfmPass.length()<6)
              {
                  edtCnfrmPass.setError("Password does not Match");
              }
              else
              {
                  edtCnfrmPass.setError(null);
              }


              break;

            }


            //go to next activity

            case R.id.go_To_Login:
         {
             Intent it= new Intent(RegistrationActivity.this, LoginActivity.class);
             startActivity(it);
             break;
         }

        }
    }
}

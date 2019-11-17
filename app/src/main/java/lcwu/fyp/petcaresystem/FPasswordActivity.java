package lcwu.fyp.petcaresystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnContinue;
    EditText edtconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpassword);

        btnContinue = findViewById(R.id.btnContinue);
        edtconfirm = findViewById(R.id.edtconfirm);

        btnContinue.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnContinue:{
                String strconfirm= edtconfirm.getText().toString();

                if (strconfirm.length()<6){
                    edtconfirm.setError("Enter A Valid Password");
                }
                else {
                    edtconfirm.setError(null);
                }
                break;
            }
        }

    }
}

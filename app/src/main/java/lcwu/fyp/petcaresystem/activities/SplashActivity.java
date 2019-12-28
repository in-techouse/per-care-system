package lcwu.fyp.petcaresystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.director.Session;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish()
            {
                Session session= new Session(SplashActivity.this);
                if(session.getUser() == null){
                    Intent it = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(it);
                    finish();

                }
                else{
                    Intent it = new Intent(SplashActivity.this, Dashboard.class);
                    startActivity(it);
                    finish();

                }

            }
        }.start();
    }
}

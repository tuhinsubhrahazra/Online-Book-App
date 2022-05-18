package com.example.introduction.onlinebookapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class SplashScreen extends AppCompatActivity {
    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        skip=findViewById(R.id.btn_skip);

        Handler handler =new Handler();
        localSession  session= new localSession(SplashScreen.this);//created  the instance of handler from android os
        handler.postDelayed(() -> {
           /* skip.setOnClickListener(view -> {
                startActivity(new Intent(SplashScreen.this, LoginPageActivity.class));
            });
         Intent intent = new Intent(SplashScreen.this,LoginPageActivity.class);
                //routing from one activity to another
                startActivity(intent);
        },2000);*/
            if (session.checkSession()){
                startActivity(new Intent(SplashScreen.this,HomeActivity.class));
            }
            else {
                //the action navigation need to be applied
                Intent intent = new Intent(SplashScreen.this, LoginPageActivity.class);
                //routing from one activity to another
                startActivity(intent);
            }
            //destroying the activity lifecycle
            SplashScreen.this.finish();
        },1000);

    }
}
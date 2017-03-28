package com.example.devesh.opendata;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.sql.Time;
import java.util.Calendar;
import java.util.Timer;

public class SplashActivity extends Activity {
    public static final int DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              startActivity(new Intent(SplashActivity.this,MainActivity.class));
              finish();
          }
      },DISPLAY_LENGTH);

    }
/*
    public void  delay(){
        long  curr_time = SystemClock.uptimeMillis();
        while (SystemClock.uptimeMillis()-curr_time<3000){

        }
        Log.d("Splash","Delay Complete");

    }*/
}

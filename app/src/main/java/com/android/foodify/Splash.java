package com.android.foodify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.android.foodify.SharedPreferences.SharedPreferences;

public class Splash extends AppCompatActivity {


    SharedPreferences launcherManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        launcherManager = new SharedPreferences(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // For showing slider screen for the only 1st Time
//                if(launcherManager.isFirstTime()){
//                    launcherManager.setFirstLunch(false);
//                    startActivity(new Intent(getApplicationContext(),Slider.class));
//
//                }else if (SharedPreferences.getPREF_userId(getApplicationContext()).length() != 0) {
//                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                } else {
//                    startActivity(new Intent(getApplicationContext(),Login.class));
//                }
                startActivity(new Intent(getApplicationContext(), Slider.class));

            }
        }, 1000);
    }
}
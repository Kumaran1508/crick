package com.kumaran.cricketscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TimerTask timerTask;
    private Intent intent;
    private Timer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        timer = new Timer();
        intent = new Intent();



    }

    @Override
    protected void onStart() {
        super.onStart();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intent.setClass(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        };
        timer.schedule(timerTask,1500);
    }
}
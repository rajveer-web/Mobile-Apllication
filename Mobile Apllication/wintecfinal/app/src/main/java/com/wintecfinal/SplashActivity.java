package com.wintecfinal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wintec.wintecfinal.R;

public class SplashActivity extends AppCompatActivity {
    String TAG = "SplashActivity";
    Handler handler = new Handler();
    TextView splashMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashMsg = (TextView) findViewById(R.id.splashMsg);
        splashMsg.setVisibility(View.INVISIBLE);

        startApplication(1000);
    }

    private void startApplication(long sleepTime) {
        handler.postDelayed(startApp, sleepTime);
    }

    Runnable startApp = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacks(startApp);
            if (Utils.getPref(SplashActivity.this, "descAccepted", false)) {
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(SplashActivity.this, DisActivity.class);
                startActivity(i);
                finish();
            }
        }
    };

    @Override
    public void onDestroy() {
        try {
            handler.removeCallbacks(startApp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}

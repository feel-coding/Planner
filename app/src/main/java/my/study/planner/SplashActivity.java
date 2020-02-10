package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                boolean lock = sharedPreference.getBoolean("password", false);
                if(lock) {
                    Intent intent = new Intent(SplashActivity.this, PasswordActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

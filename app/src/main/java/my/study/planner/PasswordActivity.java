package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PasswordActivity extends AppCompatActivity {
    String password;
    StringBuilder inputPassword = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        password = sharedPreferences.getString("stringPassword", "");
    }

    public void dialClick(View v) {
        switch (v.getId()) {
            case R.id.dial_one:
                inputPassword.append("1");
                break;
            case R.id.dial_two:
                inputPassword.append("2");
                break;
            case R.id.dial_three:
                inputPassword.append("3");
                break;
            case R.id.dial_four:
                inputPassword.append("4");
                break;
            case R.id.dial_five:
                inputPassword.append("5");
                break;
            case R.id.dial_six:
                inputPassword.append("6");
                break;
            case R.id.dial_seven:
                inputPassword.append("7");
                break;
            case R.id.dial_eight:
                inputPassword.append("8");
                break;
            case R.id.dial_nine:
                inputPassword.append("9");
                break;
            case R.id.dial_zero:
                inputPassword.append("0");
                break;
            case R.id.dial_backspace:
                if (inputPassword.length() > 0) {
                    inputPassword.deleteCharAt(inputPassword.length() - 1);
                    if (inputPassword.length() == 0)
                        findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    else if (inputPassword.length() == 1)
                        findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    else if (inputPassword.length() == 2)
                        findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                }
                break;
        }
        Log.d("lengthlength", "첫번째 비번 길이 : " + inputPassword.length());
        switch (inputPassword.length()) {
            case 1:
                findViewById(R.id.first).setBackground(getDrawable(R.drawable.full_round));
                break;
            case 2:
                findViewById(R.id.second).setBackground(getDrawable(R.drawable.full_round));
                break;
            case 3:
                findViewById(R.id.third).setBackground(getDrawable(R.drawable.full_round));
                break;
            case 4:
                findViewById(R.id.fourth).setBackground(getDrawable(R.drawable.full_round));
                if(inputPassword.toString().equals(password)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    inputPassword = new StringBuilder();
                    ((TextView)findViewById(R.id.inputPassword)).setText("비밀번호가 일치하지 않습니다.");

                    findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.fourth).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                }
        }
    }
}

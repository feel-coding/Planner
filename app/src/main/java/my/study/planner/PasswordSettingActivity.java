package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

public class PasswordSettingActivity extends AppCompatActivity {

    StringBuilder firstPassword = new StringBuilder();
    StringBuilder secondPassword = new StringBuilder();
    int tryCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean("password", false);
        editor.apply();
        finish();
    }

    public void dialClick(View v) {
        if (tryCount == 0) {
            switch (v.getId()) {
                case R.id.dial_one:
                    firstPassword.append("1");
                    break;
                case R.id.dial_two:
                    firstPassword.append("2");
                    break;
                case R.id.dial_three:
                    firstPassword.append("3");
                    break;
                case R.id.dial_four:
                    firstPassword.append("4");
                    break;
                case R.id.dial_five:
                    firstPassword.append("5");
                    break;
                case R.id.dial_six:
                    firstPassword.append("6");
                    break;
                case R.id.dial_seven:
                    firstPassword.append("7");
                    break;
                case R.id.dial_eight:
                    firstPassword.append("8");
                    break;
                case R.id.dial_nine:
                    firstPassword.append("9");
                    break;
                case R.id.dial_zero:
                    firstPassword.append("0");
                    break;
                case R.id.dial_backspace:
                    if(firstPassword.length() > 0) {
                        firstPassword.deleteCharAt(firstPassword.length() - 1);
                        if (firstPassword.length() == 0)
                            findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                        else if (firstPassword.length() == 1)
                            findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                        else if (firstPassword.length() == 2)
                            findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    }
                    break;
            }
            Log.d("lengthlength", "첫번째 비번 길이 : " + firstPassword.length());
            switch (firstPassword.length()) {
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
                    findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.fourth).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    tryCount++;

                    Log.d("lengthlength", firstPassword.toString());
            }
        }
        else if(tryCount == 1) {
            switch (v.getId()) {
                case R.id.dial_one:
                    secondPassword.append("1");
                    break;
                case R.id.dial_two:
                    secondPassword.append("2");
                    break;
                case R.id.dial_three:
                    secondPassword.append("3");
                    break;
                case R.id.dial_four:
                    secondPassword.append("4");
                    break;
                case R.id.dial_five:
                    secondPassword.append("5");
                    break;
                case R.id.dial_six:
                    secondPassword.append("6");
                    break;
                case R.id.dial_seven:
                    secondPassword.append("7");
                    break;
                case R.id.dial_eight:
                    secondPassword.append("8");
                    break;
                case R.id.dial_nine:
                    secondPassword.append("9");
                    break;
                case R.id.dial_zero:
                    secondPassword.append("0");
                    break;
                case R.id.dial_backspace:
                    if(secondPassword.length() > 0) {
                        secondPassword.deleteCharAt(secondPassword.length() - 1);
                        if (secondPassword.length() == 0)
                            findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                        else if (secondPassword.length() == 1)
                            findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                        else if (secondPassword.length() == 2)
                            findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    }
                    break;
            }
            Log.d("lengthlength", "두번째 비번 길이 : " + secondPassword.length());
            switch (secondPassword.length()) {
                case 1:
                    findViewById(R.id.first).setBackground(getDrawable(R.drawable.full_round));
                    findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.fourth).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    break;
                case 2:
                    findViewById(R.id.first).setBackground(getDrawable(R.drawable.full_round));
                    findViewById(R.id.second).setBackground(getDrawable(R.drawable.full_round));
                    findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.fourth).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    break;
                case 3:
                    findViewById(R.id.first).setBackground(getDrawable(R.drawable.full_round));
                    findViewById(R.id.second).setBackground(getDrawable(R.drawable.full_round));
                    findViewById(R.id.third).setBackground(getDrawable(R.drawable.full_round));
                    findViewById(R.id.fourth).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    break;
                case 4:
                    findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.fourth).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    Log.d("lengthlength", secondPassword.toString());
                    if(firstPassword.toString().equals(secondPassword.toString())) {
                        finish();
                    }
                    else {
                        tryCount = 0;
                    }
            }
        }
    }
}

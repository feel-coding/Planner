package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ChangePasswordActivity extends AppCompatActivity {
    StringBuilder firstPassword = new StringBuilder();
    StringBuilder secondPassword = new StringBuilder();
    int tryCount = 0;
    StringBuilder previousPassword = new StringBuilder();
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        password = sharedPreferences.getString("stringPassword", "");
    }
    public void dialClick(View v) {
        if (tryCount == 0) {
            switch (v.getId()) {
                case R.id.dial_one:
                    previousPassword.append("1");
                    break;
                case R.id.dial_two:
                    previousPassword.append("2");
                    break;
                case R.id.dial_three:
                    previousPassword.append("3");
                    break;
                case R.id.dial_four:
                    previousPassword.append("4");
                    break;
                case R.id.dial_five:
                    previousPassword.append("5");
                    break;
                case R.id.dial_six:
                    previousPassword.append("6");
                    break;
                case R.id.dial_seven:
                    previousPassword.append("7");
                    break;
                case R.id.dial_eight:
                    previousPassword.append("8");
                    break;
                case R.id.dial_nine:
                    previousPassword.append("9");
                    break;
                case R.id.dial_zero:
                    previousPassword.append("0");
                    break;
                case R.id.dial_backspace:
                    if(previousPassword.length() > 0) {
                        previousPassword.deleteCharAt(previousPassword.length() - 1);
                        if (previousPassword.length() == 0)
                            findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                        else if (previousPassword.length() == 1)
                            findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                        else if (previousPassword.length() == 2)
                            findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    }
                    break;
            }
            switch (previousPassword.length()) {
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
                    TextView tv = findViewById(R.id.textView);
                    if(password.equals(previousPassword.toString())) {
                        tv.setText("새로 변경할 비밀번호를 입력해주세요");
                        tryCount++;
                    }
                    else {
                        tv.setText("비밀번호가 일치하지 않습니다.");
                        previousPassword = new StringBuilder();
                    }
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
                    tryCount++;
            }
        }
        else if(tryCount == 2) {
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
                    if(firstPassword.toString().equals(secondPassword.toString())) {
                        SharedPreferences sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("stringPassword", firstPassword.toString());
                        editor.apply();
                        finish();
                    }
                    else {
                        tryCount = 1;
                    }
            }
        }
    }
}

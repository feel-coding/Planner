package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
                    firstPassword.deleteCharAt(firstPassword.length() - 1);
                    if (firstPassword.length() == 0)
                        findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    else if (firstPassword.length() == 1)
                        findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    else if (firstPassword.length() == 2)
                        findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    break;
            }
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
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {

                    }
                    findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.fourth).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    tryCount++;
            }
        }
        if(tryCount == 1) {
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
                    secondPassword.deleteCharAt(secondPassword.length() - 1);
                    if (secondPassword.length() == 0)
                        findViewById(R.id.first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    else if (secondPassword.length() == 1)
                        findViewById(R.id.second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    else if (secondPassword.length() == 2)
                        findViewById(R.id.third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    break;
            }
            switch (secondPassword.length()) {
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
                    if(firstPassword.equals(secondPassword)) {
                        finish();
                    }
                    else {
                        tryCount = 0;
                    }
            }
        }
    }
}

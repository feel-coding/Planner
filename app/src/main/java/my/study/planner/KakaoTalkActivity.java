package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class KakaoTalkActivity extends AppCompatActivity {
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("kako", "called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_talk);
        s = getIntent().getStringExtra("s");
        EditText editText = findViewById(R.id.kakaotalk_edit);
        editText.setText(s);
    }
    public void send(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, s.toString());
        intent.setPackage("com.kakao.talk");
        startActivity(intent);
        finish();
    }
}

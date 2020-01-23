package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class KakaoTalkActivity extends AppCompatActivity {
    String when = "이따가";
    String messageType = "하라고 꼭 말해주세요";
    String s;
    String fullSentence;
    Spinner spinnerWhen;
    Spinner spinnerMessageType;
    ArrayAdapter<String> whenAdapter;
    ArrayAdapter<String> messageAdapter;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_talk);
        editText = findViewById(R.id.kakaotalk_edit);
        spinnerWhen = findViewById(R.id.when);
        spinnerMessageType = findViewById(R.id.messageType);
        final String[] where = new String[]{"이따가","집 가서","학교에서", "회사에서"};
        final String[] type = new String[]{"꼭 말해줘","꼭 말해줘요","꼭 말해주세요"};
        whenAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, where);

        messageAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,type);
        spinnerWhen.setAdapter(whenAdapter);
        spinnerMessageType.setAdapter(messageAdapter);

        spinnerWhen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                when = whenAdapter.getItem(position);
                editText.setText(when + "\n" + s + messageType);
                fullSentence = when + " " + s + messageType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMessageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                messageType = messageAdapter.getItem(position);
                editText.setText(when + "\n" + s + messageType);
                fullSentence = when + " " + s + messageType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s = getIntent().getStringExtra("s");
        fullSentence = when + "\n" + s + messageType;
        editText.setText(fullSentence);
    }
    public void send(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
        intent.setPackage("com.kakao.talk");
        startActivity(intent);
        finish();
    }
}

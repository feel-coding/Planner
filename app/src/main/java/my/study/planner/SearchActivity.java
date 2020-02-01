package my.study.planner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText editText;
    ListView lv;
    SQLiteDatabase db;
    String searchWord = "";
    DBHelper helper;
    ArrayList<Planner> al;
    DateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.tb2);
        setSupportActionBar(toolbar);
        al = new ArrayList<>();
        lv = findViewById(R.id.search_result);
        adapter = new DateAdapter(this, al, R.layout.row_with_date);
        lv.setAdapter(adapter);
        helper = new DBHelper(this);
        editText = findViewById(R.id.search_edit);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchWord = editText.getText().toString();
                al.clear();
//                al = new ArrayList<>();
//                adapter = new DateAdapter(SearchActivity.this, al, R.layout.row_with_date);
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d("scsc", searchWord);
                    if (searchWord.equals("")) {
                        Toast.makeText(getApplicationContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("scsc", "else 문 진입");
                        db = helper.getReadableDatabase();
                        Cursor c = db.rawQuery("select * from planners", null);
                        while (c.moveToNext()) {
                            String s = c.getString(1);
                            if (s.contains(searchWord)) {
                                al.add(new Planner(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5)));
                            }
                        }
                        Log.d("scsc", "" + al);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        if (al.size() == 0) {
                            Toast.makeText(getApplicationContext(), "검색 결과가 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                    v.setFocusableInTouchMode(true);
                    v.setFocusable(true);

                }
                return true;
            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

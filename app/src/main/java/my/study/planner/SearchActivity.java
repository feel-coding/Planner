package my.study.planner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText editText;
    ListView lv;
    SQLiteDatabase db;
    String searchWord;
    DBHelper helper;
    ArrayList<Planner> al = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.tb2);
        setSupportActionBar(toolbar);
        lv = findViewById(R.id.search_result);
        adapter = new MyAdapter(this, al, R.layout.row);
        lv.setAdapter(adapter);
        helper = new DBHelper(this);
        editText = findViewById(R.id.search_edit);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        searchWord = editText.getText().toString();
                        db = helper.getReadableDatabase();
                        Cursor c = db.rawQuery("select * from planners", null);
                        while(c.moveToNext()) {
                            String s = c.getString(1);
                            if (s.contains(searchWord)) {
                                al.add(new Planner(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3)));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        break;

                    default:
                        Toast.makeText(getApplicationContext(), "기본", Toast.LENGTH_LONG).show();
                        return false;
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
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

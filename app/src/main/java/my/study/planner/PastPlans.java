package my.study.planner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class PastPlans extends AppCompatActivity {

    SQLiteDatabase db;
    DBHelper helper;
    ArrayList<Planner> al = new ArrayList<>();
    ListView lv;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_plans);
        Toolbar toolbar = findViewById(R.id.past_plan_tb);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);

        helper = new DBHelper(this);
        lv = findViewById(R.id.list);
        adapter = new MyAdapter(this, al, R.layout.row);
        lv.setAdapter(adapter);
        Toolbar tb = findViewById(R.id.past_plan_tb);
        String date = getIntent().getStringExtra("date");
        tb.setTitle(date);
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from planners", null);
        while (c.moveToNext()) {
            Log.d("datedate", "id: " + c.getLong(0) + ", todo: " +  c.getString(1) + ", date: " + c.getString(2));
            Planner planner = new Planner(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5));
            if (date.equals(c.getString(2)))
                al.add(planner);
        }
        adapter.notifyDataSetChanged();
        c.close();
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

package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import static my.study.planner.MainActivity.helper;

public class PastPlans extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<Planner> al = new ArrayList<>();
    ListView lv;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_plans);
        lv = findViewById(R.id.list);
        adapter = new MyAdapter(this, al, R.layout.row);
        lv.setAdapter(adapter);
        helper = new DBHelper(this);
        String date = getIntent().getStringExtra("date");
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from planners where date=" + date, null);
        while (c.moveToNext()) {
            Planner planner = new Planner(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3));
            al.add(planner);
        }
        adapter.notifyDataSetChanged();
        c.close();
        helper.close();
    }
}

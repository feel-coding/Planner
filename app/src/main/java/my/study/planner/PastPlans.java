package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
        helper = new DBHelper(this);
        lv = findViewById(R.id.list);
        adapter = new MyAdapter(this, al, R.layout.row);
        lv.setAdapter(adapter);
        String date = getIntent().getStringExtra("date");
        Log.d("datedate", date);
        db = SQLiteDatabase.openDatabase("/data/user/0/my.study.planner/databases/planners.db", null, SQLiteDatabase.OPEN_READONLY);
        Cursor c = db.rawQuery("select * from planners where date=" + date, null);
        while (c.moveToNext()) {
            Log.d("datedate", c.getString(1));
            Planner planner = new Planner(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3));
            al.add(planner);
        }
        adapter.notifyDataSetChanged();
        c.close();
    }
}

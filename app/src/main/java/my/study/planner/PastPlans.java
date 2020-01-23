package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        Toolbar tb = findViewById(R.id.past_plan_tb);
        String date = getIntent().getStringExtra("date");
        tb.setTitle(date);
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from planners", null);
        while (c.moveToNext()) {
            Log.d("datedate", "id: " + c.getLong(0) + ", todo: " +  c.getString(1) + ", date: " + c.getString(2));
            Planner planner = new Planner(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3));
            if (date.equals(c.getString(2)))
                al.add(planner);
        }
        adapter.notifyDataSetChanged();
        c.close();
    }
}

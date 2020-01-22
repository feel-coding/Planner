package my.study.planner;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    DBHelper helper;
    ArrayList<Planner> al = new ArrayList<>();
    SelectionAdapter adapter;
    EditText editText;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            ArrayList<Planner> selected = new ArrayList<>();
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL); //롱클릭일 때에만 복수 선택이 가능하도록 롱클릭 메소드 안에 넣음
                lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener(){
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                        if(checked) {
                            adapter.setNewSelection(position, checked);
                            selected.add(al.get(position));
                        }
                        else { //사용자가 선택했던 아이템을 다시 한 번 더 눌러서 취소할 경우
                            adapter.removeSelection(position);
                            selected.remove(al.get(position));
                        }
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        MenuInflater inflater = getMenuInflater();
                        inflater.inflate(R.menu.contextual,menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_todo:
                                DBHelper dbHelper = new DBHelper(MainActivity.this);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                Planner planner = al.get(position);
                                db.delete("contacts", "_id=" + planner.id, null);
                                al.remove(position);
                                adapter.notifyDataSetChanged();
                                break;
                            case R.id.share_todo:
                                break;
                        }
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {

                    }
                });
                return false;
            }
        });
        editText = findViewById(R.id.edit);
        helper = new DBHelper(this);
        adapter = new SelectionAdapter(this, al, R.layout.row);
        lv.setAdapter(adapter);
        getTodoList();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view) {
        db = helper.getWritableDatabase();
        ContentValues values;
        String n = editText.getText().toString(); //getText는 String 타입이 아니라 CharSequence 타입이기 때문에
        values = new ContentValues();
        values.put("todo", n);
        values.put("done", 0);
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String d = date.format(dateTimeFormatter);
        values.put("date", d);
        long id = db.insert("planners", null, values);
        Planner planner = new Planner(id, n, d, 0);
        al.add(planner);
        editText.setText("");
        adapter.notifyDataSetChanged();
    }
    void getTodoList () {
        db = helper.getReadableDatabase();
        Log.d("datedate",db.toString());
        Cursor c = db.query("planners", new String[]{"_id", "todo", "date", "done"}, null, null, null, null, null);
        while (c.moveToNext()) {
            Log.d("datedate", c.getString(1) + ", " + c.getString(2));
            al.add(new Planner(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3)));
        }
        c.close();
        helper.close();
    }
}
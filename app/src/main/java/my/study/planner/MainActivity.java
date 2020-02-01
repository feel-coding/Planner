package my.study.planner;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    DBHelper helper;
    ArrayList<Planner> al = new ArrayList<>();
    MyAdapter adapter;
    EditText editText;
    SQLiteDatabase db;
    Toolbar toolbar;
    Button add;
    int mode = 0;
    int selectedIndex;
    ArrayAdapter<String> categoryAdapter;
    Spinner spinner;
    long pressedTime;
    String cat;
    int catNum;
    String[] category;
    InputMethodManager imm;
    EveryHelper everyHelper;
    SQLiteDatabase everyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        lv = findViewById(R.id.lv);
        spinner = findViewById(R.id.category);
        category = new String[]{"할 일", "업무", "공부", "약속"};
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category);
        spinner.setAdapter(categoryAdapter);
        editText = findViewById(R.id.edit);
        helper = new DBHelper(this);
        everyHelper = new EveryHelper(this);
        adapter = new MyAdapter(this, al, R.layout.row);
        lv.setAdapter(adapter);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText(al.get(position).todo);
                editText.setHint("할 일을 수정해보세요");
                add.setBackgroundResource(R.drawable.update);
                mode = 1;
                selectedIndex = position;
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat = categoryAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            int n = 0;
            ArrayList<Planner> selected = new ArrayList<>();
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    adapter.setNewSelection(position, checked);
                    selected.add(al.get(position));
                    n++;
                } else { //사용자가 선택했던 아이템을 다시 한 번 더 눌러서 취소할 경우
                    adapter.removeSelection(position);
                    selected.remove(al.get(position));
                    n--;
                }
                mode.setTitle(n + "개 선택됨");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.contextual, menu);
                toolbar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_todo:
                        db = helper.getWritableDatabase();
                        for (Planner p : selected) {
                            db.delete("planners", "_id=" + p.id, null);
                            al.remove(p);
                            adapter.notifyDataSetChanged();
                        }
                        n = 0;
                        adapter.clearSelection();
                        mode.finish();
                        break;
                    case R.id.share_todo:
                        StringBuilder s = new StringBuilder("");
                        for (Planner p : selected) {
                            s.append(p.todo + "\n");
                        }
//                                s.replace(s.length() - 2, s.length(), " ");
                        s.append("하라고 ");
                        Intent intent = new Intent(MainActivity.this, KakaoTalkActivity.class);
                        intent.putExtra("s", s.toString());
                        startActivity(intent);
                        selected = new ArrayList<>();
                        n = 0;
                        s = new StringBuilder("");
                        adapter.clearSelection();
                        adapter.notifyDataSetChanged();
                        mode.finish();
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.clearSelection();
                n = 0;
                selected = new ArrayList<>();
                toolbar.setVisibility(View.VISIBLE);
            }
        });

        getTodoList();
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this, EverydayActivity.class);
                startActivity(i);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (mode == 1) {
            mode = 0;
            add.setBackgroundResource(R.drawable.add);
            editText.setHint("할 일을 추가해보세요");
            editText.setText("");
        }
        else {
            if (System.currentTimeMillis() - pressedTime < 20000) {
                finishAffinity();
                return;
            }
            Toast.makeText(this, "종료하려면 한번 더 눌러주세요", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
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
        if(id == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_sync) {
            ArrayList<Long> haveToUpdate = new ArrayList<>();
            al.clear();
            db = helper.getWritableDatabase();
            everyDb = everyHelper.getReadableDatabase();
            Cursor c = everyDb.query("every", new String[]{"_id", "todo", "cycle", "date", "day", "category", "dbin"}, null, null, null, null, null);
            LocalDate date = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String today = date.format(dateTimeFormatter);
            while(c.moveToNext()) {
                if(c.getInt(2) == 0) {
                    if(c.getInt(6) == 0) {
                        ContentValues values = new ContentValues();
                        values.put("todo", c.getString(1));
                        values.put("date", today);
                        values.put("done", 0);
                        values.put("category", c.getInt(5));
                        values.put("everyid", c.getLong(0));
                        long idid = db.insert("planners", null, values);
                        haveToUpdate.add(idid);
                    }
                }
                else if(c.getInt(2) == 2) {
                    if(c.getInt(3) == Integer.parseInt(today.substring(8, 10))) {
                        if(c.getInt(6) == 0) {
                            ContentValues values = new ContentValues();
                            values.put("todo", c.getString(1));
                            values.put("date", today);
                            values.put("done", 0);
                            values.put("category", c.getInt(5));
                            long idid = db.insert("planners", null, values);
                            haveToUpdate.add(idid);
                        }
                    }
                }
            }
            everyDb.close();
            db.close();
            c.close();
            db = everyHelper.getWritableDatabase();
            for(long idid: haveToUpdate) {
                ContentValues values = new ContentValues();
                values.put("dbin", 1); //db에 썼다고 업데이트
                db.update("every", values, "_id=" + idid, null);
            }
            db.close();
            c.close();
            everyHelper.close();
            db = helper.getReadableDatabase();
            Log.d("datedate", db.toString());
            c = db.query("planners", new String[]{"_id", "todo", "date", "done", "category", "everyid"}, null, null, null, null, null);
            while (c.moveToNext()) {
                if (c.getString(2).equals(today))
                    al.add(new Planner(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5)));
            }
            c.close();
            helper.close();
            db.close();
            adapter = new MyAdapter(MainActivity.this, al, R.layout.row);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
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

        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void onClick(View view) {
        if(editText.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "할 일을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        db = helper.getWritableDatabase();
        ContentValues values;
        String n = editText.getText().toString(); //getText는 String 타입이 아니라 CharSequence 타입이기 때문에
        values = new ContentValues();
        values.put("todo", n);
        switch (cat) {
            case "할 일":
                catNum = 0; break;
            case "업무":
                catNum = 1; break;
            case "공부":
                catNum = 2; break;
            case "약속":
                catNum = 3; break;
        }
        values.put("category", catNum);
        if (mode == 0) {
            values.put("done", 0);
            LocalDate date = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String d = date.format(dateTimeFormatter);
            values.put("date", d);
            values.put("category", catNum);
            values.put("everyid", -1);
            long id = db.insert("planners", null, values);
            Planner planner = new Planner(id, n, d, 0, catNum, -1);
            al.add(planner);
        }
        else {
            values.put("done", al.get(selectedIndex).done);
            values.put("date", al.get(selectedIndex).date);
            db.update("planners", values, "_id=" + al.get(selectedIndex).id, null);
            al.get(selectedIndex).todo = editText.getText().toString();
            al.get(selectedIndex).category = catNum;
            mode = 0;
            add.setBackgroundResource(R.drawable.add);
            editText.setHint("할 일을 추가해보세요");
        }
        editText.setText("");
        adapter.notifyDataSetChanged();
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    void getTodoList() {
        ArrayList<Long> haveToUpdate = new ArrayList<>();
        db = helper.getWritableDatabase();
        everyDb = everyHelper.getReadableDatabase();
        Cursor c = everyDb.query("every", new String[]{"_id", "todo", "cycle", "date", "day", "category", "dbin"}, null, null, null, null, null);
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = date.format(dateTimeFormatter);
        while(c.moveToNext()) {
            if(c.getInt(2) == 0) {
                if(c.getInt(6) == 0) {
                    ContentValues values = new ContentValues();
                    values.put("todo", c.getString(1));
                    values.put("date", today);
                    values.put("done", 0);
                    values.put("category", c.getInt(5));
                    values.put("everyid", c.getLong(0));
                    long id = db.insert("planners", null, values);
                    haveToUpdate.add(id);
                }
            }
            else if(c.getInt(2) == 2) {
                if(c.getInt(3) == Integer.parseInt(today.substring(8, 10))) {
                    if(c.getInt(6) == 0) {
                        ContentValues values = new ContentValues();
                        values.put("todo", c.getString(1));
                        values.put("date", today);
                        values.put("done", 0);
                        values.put("category", c.getInt(5));
                        values.put("everyid", c.getLong(0));
                        long id = db.insert("planners", null, values);
                        haveToUpdate.add(id);
                    }
                }
            }
        }
        everyDb.close();
        db.close();
        c.close();
        db = everyHelper.getWritableDatabase();
        for(long id: haveToUpdate) {
            ContentValues values = new ContentValues();
            values.put("dbin", 1); //db에 썼다고 업데이트
            db.update("every", values, "_id=" + id, null);
        }
        db.close();
        c.close();
        everyHelper.close();
        db = helper.getReadableDatabase();
        Log.d("datedate", db.toString());
        c = db.query("planners", new String[]{"_id", "todo", "date", "done", "category", "everyid"}, null, null, null, null, null);
        while (c.moveToNext()) {
            Log.d("ididid", c.getString(1) + " " + c.getLong(5));
            if (c.getString(2).equals(today))
                al.add(new Planner(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getLong(5)));
        }
        c.close();
        helper.close();
        db.close();
    }
}
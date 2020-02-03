package my.study.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EverydayActivity extends AppCompatActivity {

    Button everyday;
    Button everyWeek;
    Button everyMonth;
    LinearLayout ll;
    Button monday;
    Button tuesday;
    Button wednesday;
    Button thursday;
    Button friday;
    Button saturday;
    Button sunday;
    LinearLayout days;
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    Button ten;
    Button eleven;
    Button twelve;
    Button thirteen;
    Button fourteen;
    Button fifteen;
    Button sixteen;
    Button seventeen;
    Button eighteen;
    Button nineteen;
    Button twenty;
    Button twentyone;
    Button twentytwo;
    Button twentythree;
    Button twentyfour;
    Button twentyfive;
    Button twentysix;
    Button twentyseven;
    Button twentyeight;
    EditText edit;
    int mode = -1; //0은 매일, 1은 매주, 2는 매달
    int selectedDay = -1;
    int selectedDate = -1;
    ListView lv;
    Spinner spinner;
    String cat;
    EveryAdapter adapter;
    ArrayList<EveryPlanner> al = new ArrayList<>();
    ArrayAdapter<String> categoryAdapter;
    String[] category;
    SQLiteDatabase db;
    EveryHelper helper;
    int selecting = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyday);
        everyday = findViewById(R.id.everyday);
        everyWeek = findViewById(R.id.everyweek);
        everyMonth = findViewById(R.id.everymonth);
        ll = findViewById(R.id.week);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);
        days = findViewById(R.id.days);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        ten = findViewById(R.id.ten);
        eleven = findViewById(R.id.eleven);
        twelve = findViewById(R.id.tweleve);
        thirteen = findViewById(R.id.thirteen);
        fourteen = findViewById(R.id.fourteen);
        fifteen = findViewById(R.id.fifteen);
        sixteen = findViewById(R.id.sixteen);
        seventeen = findViewById(R.id.seventeen);
        eighteen = findViewById(R.id.eighteen);
        nineteen = findViewById(R.id.nineteen);
        twenty = findViewById(R.id.twenty);
        twentyone = findViewById(R.id.twentyone);
        twentytwo = findViewById(R.id.twentytwo);
        twentythree = findViewById(R.id.twentythree);
        twentyfour = findViewById(R.id.twentyfour);
        twentyfive = findViewById(R.id.twentyfive);
        twentysix = findViewById(R.id.twentysix);
        twentyseven = findViewById(R.id.twentyseven);
        twentyeight = findViewById(R.id.twentyeight);
        edit = findViewById(R.id.every_editText);
        lv = findViewById(R.id.everylist);
        helper = new EveryHelper(this);
        adapter = new EveryAdapter(this, al, R.layout.every_row);
        lv.setAdapter(adapter);
        Log.d("evevev", "1");
        category = new String[]{"할 일", "업무", "공부", "약속"};
        Log.d("evevev", "2");
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category);
        spinner = findViewById(R.id.everycategory);
        spinner.setAdapter(categoryAdapter);
        Log.d("evevev", "3");
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
            ArrayList<EveryPlanner> selected = new ArrayList<>();
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
                inflater.inflate(R.menu.delete, menu);
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
                        for (EveryPlanner p : selected) {
                            db.delete("every", "_id=" + p.id, null);
                            al.remove(p);
                            adapter.notifyDataSetChanged();
                        }
                        n = 0;
                        adapter.notifyDataSetChanged();
                        mode.finish();
                        findViewById(R.id.unchecked).setVisibility(View.INVISIBLE);
                        findViewById(R.id.checked).setVisibility(View.INVISIBLE);
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.clearSelection();
                n = 0;
                selected = new ArrayList<>();
                findViewById(R.id.unchecked).setVisibility(View.INVISIBLE);
                findViewById(R.id.checked).setVisibility(View.INVISIBLE);
            }
        });
        getEveryTodoList();
    }
    @Override
    public void onBackPressed() {
        if (selecting == 1) {
            selecting = 0;
            days.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
        }
        else {
            finish();
        }
    }
    public void every(View v){
        selecting = 1;
        switch (v.getId()) {
            case R.id.everyday:
                ll.setVisibility(View.GONE);
                days.setVisibility(View.GONE);
                edit.setHint(" 매일 해야하는 일을 추가해보세요");
                everyday.setBackgroundResource(R.drawable.round_pastel_yellow);
                everyWeek.setBackgroundResource(R.drawable.grey_round_button);
                everyMonth.setBackgroundResource(R.drawable.grey_round_button);
                mode = 0;
                break;
            case R.id.everyweek:
                ll.setVisibility(View.VISIBLE);
                days.setVisibility(View.GONE);
                edit.setHint(" 매 주 해야하는 일을 추가해보세요");
                everyWeek.setBackgroundResource(R.drawable.round_pastel_yellow);
                everyday.setBackgroundResource(R.drawable.grey_round_button);
                everyMonth.setBackgroundResource(R.drawable.grey_round_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                mode = 1;
                break;
            case R.id.everymonth:
                ll.setVisibility(View.GONE);
                days.setVisibility(View.VISIBLE);
                edit.setHint(" 매 달 해야하는 일을 추가해보세요");
                everyMonth.setBackgroundResource(R.drawable.round_pastel_yellow);
                everyday.setBackgroundResource(R.drawable.grey_round_button);
                everyWeek.setBackgroundResource(R.drawable.grey_round_button);
                monday.setBackgroundResource(R.drawable.grey_round_button);
                tuesday.setBackgroundResource(R.drawable.grey_round_button);
                wednesday.setBackgroundResource(R.drawable.grey_round_button);
                thursday.setBackgroundResource(R.drawable.grey_round_button);
                friday.setBackgroundResource(R.drawable.grey_round_button);
                saturday.setBackgroundResource(R.drawable.grey_round_button);
                sunday.setBackgroundResource(R.drawable.grey_round_button);
                mode = 2;
                break;
            case R.id.monday:
                edit.setHint("매 주 월요일마다 해야하는 일을 추가해보세요");
                monday.setBackgroundResource(R.drawable.round_pastel_yellow);
                tuesday.setBackgroundResource(R.drawable.grey_round_button);
                wednesday.setBackgroundResource(R.drawable.grey_round_button);
                thursday.setBackgroundResource(R.drawable.grey_round_button);
                friday.setBackgroundResource(R.drawable.grey_round_button);
                saturday.setBackgroundResource(R.drawable.grey_round_button);
                sunday.setBackgroundResource(R.drawable.grey_round_button);
                selectedDay = 0;
                break;
            case R.id.tuesday:
                edit.setHint("매 주 화요일마다 해야하는 일을 추가해보세요");
                tuesday.setBackgroundResource(R.drawable.round_pastel_yellow);
                monday.setBackgroundResource(R.drawable.grey_round_button);
                wednesday.setBackgroundResource(R.drawable.grey_round_button);
                thursday.setBackgroundResource(R.drawable.grey_round_button);
                friday.setBackgroundResource(R.drawable.grey_round_button);
                saturday.setBackgroundResource(R.drawable.grey_round_button);
                sunday.setBackgroundResource(R.drawable.grey_round_button);
                selectedDay = 1;
                break;
            case R.id.wednesday:
                edit.setHint("매 주 수요일마다 해야하는 일을 추가해보세요");
                wednesday.setBackgroundResource(R.drawable.round_pastel_yellow);
                tuesday.setBackgroundResource(R.drawable.grey_round_button);
                monday.setBackgroundResource(R.drawable.grey_round_button);
                thursday.setBackgroundResource(R.drawable.grey_round_button);
                friday.setBackgroundResource(R.drawable.grey_round_button);
                saturday.setBackgroundResource(R.drawable.grey_round_button);
                sunday.setBackgroundResource(R.drawable.grey_round_button);
                selectedDay = 2;
                break;
            case R.id.thursday:
                edit.setHint("매 주 목요일마다 해야하는 일을 추가해보세요");
                thursday.setBackgroundResource(R.drawable.round_pastel_yellow);
                tuesday.setBackgroundResource(R.drawable.grey_round_button);
                wednesday.setBackgroundResource(R.drawable.grey_round_button);
                monday.setBackgroundResource(R.drawable.grey_round_button);
                friday.setBackgroundResource(R.drawable.grey_round_button);
                saturday.setBackgroundResource(R.drawable.grey_round_button);
                sunday.setBackgroundResource(R.drawable.grey_round_button);
                selectedDay = 3;
                break;
            case R.id.friday:
                edit.setHint("매 주 금요일마다 해야하는 일을 추가해보세요");
                friday.setBackgroundResource(R.drawable.round_pastel_yellow);
                tuesday.setBackgroundResource(R.drawable.grey_round_button);
                wednesday.setBackgroundResource(R.drawable.grey_round_button);
                thursday.setBackgroundResource(R.drawable.grey_round_button);
                monday.setBackgroundResource(R.drawable.grey_round_button);
                saturday.setBackgroundResource(R.drawable.grey_round_button);
                sunday.setBackgroundResource(R.drawable.grey_round_button);
                selectedDay = 4;
                break;
            case R.id.saturday:
                edit.setHint("매 주 토요일마다 해야하는 일을 추가해보세요");
                saturday.setBackgroundResource(R.drawable.round_pastel_yellow);
                tuesday.setBackgroundResource(R.drawable.grey_round_button);
                wednesday.setBackgroundResource(R.drawable.grey_round_button);
                thursday.setBackgroundResource(R.drawable.grey_round_button);
                friday.setBackgroundResource(R.drawable.grey_round_button);
                monday.setBackgroundResource(R.drawable.grey_round_button);
                sunday.setBackgroundResource(R.drawable.grey_round_button);
                selectedDay = 5;
                break;
            case R.id.sunday:
                edit.setHint("매 주 일요일마다 해야하는 일을 추가해보세요");
                sunday.setBackgroundResource(R.drawable.round_pastel_yellow);
                tuesday.setBackgroundResource(R.drawable.grey_round_button);
                wednesday.setBackgroundResource(R.drawable.grey_round_button);
                thursday.setBackgroundResource(R.drawable.grey_round_button);
                friday.setBackgroundResource(R.drawable.grey_round_button);
                saturday.setBackgroundResource(R.drawable.grey_round_button);
                monday.setBackgroundResource(R.drawable.grey_round_button);
                selectedDay = 6;
                break;
            case R.id.one:
                edit.setHint("매 월 1일마다 해야하는 일을 추가해보세요");
                one.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 1;
                break;
            case R.id.two:
                edit.setHint("매 월 2일마다 해야하는 일을 추가해보세요");
                two.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 2;
                break;
            case R.id.three:
                edit.setHint("매 월 3일마다 해야하는 일을 추가해보세요");
                three.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 3;
                break;
            case R.id.four:
                edit.setHint("매 월 4일마다 해야하는 일을 추가해보세요");
                four.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 4;
                break;
            case R.id. five:
                edit.setHint("매 월 5일마다 해야하는 일을 추가해보세요");
                five.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 5;
                break;
            case R.id.six:
                edit.setHint("매 월 6일마다 해야하는 일을 추가해보세요");
                six.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 6;
                break;
            case R.id.seven:
                edit.setHint("매 월 7일마다 해야하는 일을 추가해보세요");
                seven.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 7;
                break;
            case R.id.eight:
                edit.setHint("매 월 8일마다 해야하는 일을 추가해보세요");
                eight.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 8;
                break;
            case R.id.nine:
                edit.setHint("매 월 9일마다 해야하는 일을 추가해보세요");
                nine.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 9;
                break;
            case R.id.ten:
                edit.setHint("매 월 10일마다 해야하는 일을 추가해보세요");
                ten.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 10;
                break;
            case R.id.eleven:
                edit.setHint("매 월 11일마다 해야하는 일을 추가해보세요");
                eleven.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 11;
                break;
            case R.id.tweleve:
                edit.setHint("매 월 12일마다 해야하는 일을 추가해보세요");
                twelve.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 12;
                break;
            case R.id.thirteen:
                edit.setHint("매 월 13일마다 해야하는 일을 추가해보세요");
                thirteen.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 13;
                break;
            case R.id.fourteen:
                edit.setHint("매 월 14일마다 해야하는 일을 추가해보세요");
                fourteen.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 14;
                break;
            case R.id.fifteen:
                edit.setHint("매 월 15일마다 해야하는 일을 추가해보세요");
                fifteen.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 15;
                break;
            case R.id.sixteen:
                edit.setHint("매 월 16일마다 해야하는 일을 추가해보세요");
                sixteen.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 16;
                break;
            case R.id.seventeen:
                edit.setHint("매 월 17일마다 해야하는 일을 추가해보세요");
                seventeen.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 17;
                break;
            case R.id.eighteen:
                edit.setHint("매 월 18일마다 해야하는 일을 추가해보세요");
                eighteen.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 18;

                break;
            case R.id.nineteen:
                edit.setHint("매 월 19일마다 해야하는 일을 추가해보세요");
                nineteen.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 19;
                break;
            case R.id.twenty:
                edit.setHint("매 월 20일마다 해야하는 일을 추가해보세요");
                twenty.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 20;
                break;
            case R.id.twentyone:
                edit.setHint("매 월 21일마다 해야하는 일을 추가해보세요");
                twentyone.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 21;
                break;
            case R.id.twentytwo:
                edit.setHint("매 월 22일마다 해야하는 일을 추가해보세요");
                twentytwo.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 22;
                break;
            case R.id.twentythree:
                edit.setHint("매 월 23일마다 해야하는 일을 추가해보세요");
                twentythree.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 23;
                break;
            case R.id.twentyfour:
                edit.setHint("매 월 24일마다 해야하는 일을 추가해보세요");
                twentyfour.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 24;
                break;
            case R.id.twentyfive:
                edit.setHint("매 월 25일마다 해야하는 일을 추가해보세요");
                twentyfive.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 25;
                break;
            case R.id.twentysix:
                edit.setHint("매 월 26일마다 해야하는 일을 추가해보세요");
                twentysix.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 26;
                break;
            case R.id.twentyseven:
                edit.setHint("매 월 27일마다 해야하는 일을 추가해보세요");
                twentyseven.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                twentyeight.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 27;
                break;
            case R.id.twentyeight:
                edit.setHint("매 월 28일마다 해야하는 일을 추가해보세요");
                twentyeight.setBackgroundResource(R.drawable.pastel_yellow_box_button);
                two.setBackgroundResource(R.drawable.grey_box_button);
                three.setBackgroundResource(R.drawable.grey_box_button);
                four.setBackgroundResource(R.drawable.grey_box_button);
                five.setBackgroundResource(R.drawable.grey_box_button);
                six.setBackgroundResource(R.drawable.grey_box_button);
                seven.setBackgroundResource(R.drawable.grey_box_button);
                eight.setBackgroundResource(R.drawable.grey_box_button);
                nine.setBackgroundResource(R.drawable.grey_box_button);
                ten.setBackgroundResource(R.drawable.grey_box_button);
                eleven.setBackgroundResource(R.drawable.grey_box_button);
                twelve.setBackgroundResource(R.drawable.grey_box_button);
                thirteen.setBackgroundResource(R.drawable.grey_box_button);
                fourteen.setBackgroundResource(R.drawable.grey_box_button);
                fifteen.setBackgroundResource(R.drawable.grey_box_button);
                sixteen.setBackgroundResource(R.drawable.grey_box_button);
                seventeen.setBackgroundResource(R.drawable.grey_box_button);
                eighteen.setBackgroundResource(R.drawable.grey_box_button);
                nineteen.setBackgroundResource(R.drawable.grey_box_button);
                twenty.setBackgroundResource(R.drawable.grey_box_button);
                twentyone.setBackgroundResource(R.drawable.grey_box_button);
                twentytwo.setBackgroundResource(R.drawable.grey_box_button);
                twentythree.setBackgroundResource(R.drawable.grey_box_button);
                twentyfour.setBackgroundResource(R.drawable.grey_box_button);
                twentyfive.setBackgroundResource(R.drawable.grey_box_button);
                twentysix.setBackgroundResource(R.drawable.grey_box_button);
                twentyseven.setBackgroundResource(R.drawable.grey_box_button);
                one.setBackgroundResource(R.drawable.grey_box_button);
                selectedDate = 28;
                break;
        }
    }
    public void add(View v) {
        int catNum = 0;
        if(mode == 0) { //매일

        }
        else if (mode == 1) { //매주
            if(selectedDay == -1) {
                Toast.makeText(this, "요일을 선택해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else {

            }
        }
        else if (mode == 2) { //매달
            if(selectedDate == -1) {
                Toast.makeText(this, "날짜를 선택해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else {

            }
        }
        else {
            Toast.makeText(this, "반복할 주기를 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        db = helper.getWritableDatabase();
        ContentValues values;
        String n = edit.getText().toString(); //getText는 String 타입이 아니라 CharSequence 타입을 반환한다. 따라서 String으로 바꿔줘야 한다.
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
        values.put("cycle", mode);
        switch (mode) {
            case 0:
                break;
            case 1:
                values.put("day", selectedDay);
                break;
            case 2:
                values.put("date", selectedDate);
                break;
        }
        values.put("dbin", 0);
        long id = db.insert("every", null, values);
        EveryPlanner planner = new EveryPlanner(id, n, mode, selectedDate, selectedDay, catNum);
        al.add(planner);
        adapter.notifyDataSetChanged();
        edit.setText("");
        selecting = 0;
        db.close();
    }
    void getEveryTodoList() {
        db = helper.getReadableDatabase();
        Cursor c = db.query("every", new String[]{"_id", "todo", "cycle", "date", "day", "category"}, null, null, null, null, null);
        while (c.moveToNext()) {
            al.add(new EveryPlanner(c.getLong(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5)));
        }
        c.close();
        helper.close();
    }
}

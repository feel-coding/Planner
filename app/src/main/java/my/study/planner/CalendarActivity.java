package my.study.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class CalendarActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    SimpleDateFormat dateFormat;
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    ArrayList<Planner> al = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        helper = new DBHelper(this);
        calendarView = findViewById(R.id.calendarView);
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) //0월이 1월
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new TodayDecorator());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String selectedDay = dateFormat.format(date.getDate());
                Intent i = new Intent(CalendarActivity.this, PastPlans.class);
                i.putExtra("date", selectedDay);
                startActivity(i);
            }
        });
    }
}

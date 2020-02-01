package my.study.planner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EveryHelper extends SQLiteOpenHelper {
    public EveryHelper(@Nullable Context context) {
        super(context, "every.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table every(_id integer primary key autoincrement, todo text not null, cycle integer not null, date integer, day integer, category integer not null, dbin integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists every;");
        onCreate(db);
    }
}
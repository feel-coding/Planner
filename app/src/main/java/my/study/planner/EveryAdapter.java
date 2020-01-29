package my.study.planner;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EveryAdapter extends BaseAdapter {

    private static final String SHARED_PREF_EVERYMODE = "2000";

    Activity context;
    ArrayList<Planner> al;
    int layout;
    DBHelper helper;
    SQLiteDatabase db;

    public EveryAdapter(Activity context, ArrayList<Planner> al, int layout) {
        this.context = context;
        this.al = al;
        this.layout = layout;
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null)
            view = View.inflate(context, layout, null);
        TextView flag = view.findViewById(R.id.everyFlag);
        TextView everyTodo = view.findViewById(R.id.every_todo);
        return null;
    }
}

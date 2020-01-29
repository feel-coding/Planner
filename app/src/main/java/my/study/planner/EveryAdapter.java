package my.study.planner;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class EveryAdapter extends BaseAdapter {

    Activity context;
    ArrayList<EveryPlanner> al;
    int layout;
    DBHelper helper;
    SQLiteDatabase db;

    public EveryAdapter(Activity context, ArrayList<EveryPlanner> al, int layout) {
        this.context = context;
        this.al = al;
        this.layout = layout;
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int position) {
        return al.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null)
            view = View.inflate(context, layout, null);
        TextView cycleFlag = view.findViewById(R.id.cycleFlag);
        TextView categoryFlag = view.findViewById(R.id.catFlag);
        TextView everyTodo = view.findViewById(R.id.every_todo);
        return null;
    }
}

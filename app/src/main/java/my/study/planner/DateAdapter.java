package my.study.planner;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class DateAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Planner> al;
    int layout;
    HashMap<Integer, Boolean> selected = new HashMap<>();
    DBHelper helper;
    SQLiteDatabase db;


    public DateAdapter(Activity context, ArrayList<Planner> al, int layout) {
        this.context = context;
        this.al = al;
        this.layout = layout;
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public Activity getContext() {
        return context;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null)
            view = View.inflate(context, layout, null);
        TextView todoField = view.findViewById(R.id.todoField);
        todoField.setText(al.get(position).todo);
        TextView dateField = view.findViewById(R.id.dateField);
        dateField.setText(al.get(position).date);
        Button doneButton = view.findViewById(R.id.doneButton);
        //아무것도 표시 없는건 0, 체크는 1, 세모는 2, x는 3
        final ImageView check = view.findViewById(R.id.check);
        final ImageView triangle = view.findViewById(R.id.triangle);
        final ImageView x = view.findViewById(R.id.x);
        Log.d("donedone", "읽어올 때 done은 " + al.get(position).done);
        switch (al.get(position).done) {
            case 0:
                triangle.setVisibility(View.INVISIBLE);
                check.setVisibility(View.INVISIBLE);
                x.setVisibility(View.INVISIBLE);
                break;
            case 1:triangle.setVisibility(View.INVISIBLE);
                check.setVisibility(View.VISIBLE);
                x.setVisibility(View.INVISIBLE);
                break;
            case 2:
                triangle.setVisibility(View.VISIBLE);
                check.setVisibility(View.INVISIBLE);
                x.setVisibility(View.INVISIBLE);
                break;
            case 3:
                triangle.setVisibility(View.INVISIBLE);
                check.setVisibility(View.INVISIBLE);
                x.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return al.size();
    }


    @Override
    public Object getItem(int position) {
        return al.get(position);
    }
}

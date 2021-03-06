package my.study.planner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter {

    Activity context;
    ArrayList<Planner> al;
    int layout;
    HashMap<Integer, Boolean> selected = new HashMap<>();
    DBHelper helper;
    SQLiteDatabase db;


    public MyAdapter(Activity context, ArrayList<Planner> al, int layout) {
        this.context = context;
        this.al = al;
        this.layout = layout;
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public Activity getContext() {
        return context;
    }

    public void setNewSelection(int position, boolean value) {
        selected.put(position, value);
        notifyDataSetChanged();
    }
    public void removeSelection(int position) {
        selected.remove(position);
        notifyDataSetChanged();
    }
    public void clearSelection() {
        selected = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null)
            view = View.inflate(context, layout, null);
        TextView todoField = view.findViewById(R.id.todo);
        todoField.setText(al.get(position).todo);
        Button doneButton = view.findViewById(R.id.doneButton);
        TextView categoryFlag = view.findViewById(R.id.categoryFlag);
        switch (al.get(position).category) {
            case 0:
                categoryFlag.setText("할 일");
                categoryFlag.setBackground(view.getResources().getDrawable(R.drawable.red_round_tag));
                break;
            case 1:
                categoryFlag.setText("업무");
                categoryFlag.setBackground(view.getResources().getDrawable(R.drawable.green_round_tag));
                break;
            case 2:
                categoryFlag.setText("공부");
                categoryFlag.setBackground(view.getResources().getDrawable(R.drawable.blue_round_tag));
                break;
            case 3:
                categoryFlag.setText("약속");
                categoryFlag.setBackground(view.getResources().getDrawable(R.drawable.purple_round_tag));
                break;
        }
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
        doneButton.setOnClickListener(new View.OnClickListener() {
            int count = al.get(position).done;
            @Override
            public void onClick(View v) {
                count = (al.get(position).done + 1) % 4;
                if (count == 0) {
                    triangle.setVisibility(View.INVISIBLE);
                    check.setVisibility(View.INVISIBLE);
                    x.setVisibility(View.INVISIBLE);
                }
                else if (count == 1) {
                    triangle.setVisibility(View.INVISIBLE);
                    check.setVisibility(View.VISIBLE);
                    x.setVisibility(View.INVISIBLE);
                }
                else if (count == 2) {
                    triangle.setVisibility(View.VISIBLE);
                    check.setVisibility(View.INVISIBLE);
                    x.setVisibility(View.INVISIBLE);
                }
                else if (count == 3) {
                    triangle.setVisibility(View.INVISIBLE);
                    check.setVisibility(View.INVISIBLE);
                    x.setVisibility(View.VISIBLE);
                }
                Planner p = al.get(position);
                ContentValues values = new ContentValues();
                values.put("done", count);
                db.update("planners", values, "_id=" + p.id, null);
                al.get(position).done = count;
                Log.d("donedone", "count는 " + count);
                Log.d("donedone", "done은 " + al.get(position).done);
            }
        });
        int defaultColor = ContextCompat.getColor(getContext(), android.R.color.background_light);
        view.setBackgroundColor(defaultColor);
        if(selected.get(position) != null){
            int selectedColor = ContextCompat.getColor(getContext(), R.color.pastelYellow);
            view.setBackgroundColor(selectedColor);
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
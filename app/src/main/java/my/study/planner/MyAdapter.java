package my.study.planner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
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


    public MyAdapter(Activity context, ArrayList<Planner> al, int layout) {
        this.context = context;
        this.al = al;
        this.layout = layout;
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
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null)
            view = View.inflate(context, layout, null);
        TextView todoField = view.findViewById(R.id.todo);
        todoField.setText(al.get(position).todo);
        Button doneButton = view.findViewById(R.id.doneButton);
        //아무것도 표시 없는건 0, 체크는 1, 세모는 2, x는 3
        final ImageView check = view.findViewById(R.id.check);
        final ImageView triangle = view.findViewById(R.id.triangle);
        final ImageView x = view.findViewById(R.id.x);
        doneButton.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View v) {
                count = (count + 1) % 4;
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
                else {
                    triangle.setVisibility(View.INVISIBLE);
                    check.setVisibility(View.INVISIBLE);
                    x.setVisibility(View.VISIBLE);
                }
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
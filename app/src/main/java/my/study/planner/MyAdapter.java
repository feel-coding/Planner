package my.study.planner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Activity context;
    ArrayList<Planner> al;
    int layout;


    public MyAdapter(Activity context, ArrayList<Planner> al, int layout) {
        this.context = context;
        this.al = al;
        this.layout = layout;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int i = position;
        if (view == null)
            view = View.inflate(context, layout, null);
        TextView namename = view.findViewById(R.id.namefield);
        Button delBtn = view.findViewById(R.id.delbtn);
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
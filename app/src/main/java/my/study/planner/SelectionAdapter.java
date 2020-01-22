package my.study.planner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectionAdapter extends MyAdapter {

    HashMap<Integer, Boolean> selected = new HashMap<>();

    public SelectionAdapter(Activity context, ArrayList<Planner> al, int layout) {
        super(context, al, layout);
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
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        int defaultColor = ContextCompat.getColor(getContext(), android.R.color.background_light);
        v.setBackgroundColor(defaultColor);

        //선택된 항목은 이 색상으로
        if(selected.get(position) != null){
            int selectedColor = ContextCompat.getColor(getContext(), R.color.pastelYellow);
            v.setBackgroundColor(selectedColor);
        }
        return v;
    }
}

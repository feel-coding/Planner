package my.study.planner;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class EveryAdapter extends BaseAdapter {

    Activity context;
    ArrayList<EveryPlanner> al;
    int layout;
    DBHelper helper;
    SQLiteDatabase db;
    HashMap<Integer, Boolean> selected = new HashMap<>();

    public EveryAdapter(Activity context, ArrayList<EveryPlanner> al, int layout) {
        this.context = context;
        this.al = al;
        this.layout = layout;
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
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
        everyTodo.setText(al.get(position).todo);
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
        switch (al.get(position).cycle) {
            case 0: //매일
                cycleFlag.setText("매일");
                break;
            case 1: //매주
                String day;
                switch (al.get(position).day) {
                    case 0:
                        day = "월요일";
                        break;
                    case 1:
                        day = "화요일";
                        break;
                    case 2:
                        day = "수요일";
                        break;
                    case 3:
                        day = "목요일";
                        break;
                    case 4:
                        day = "금요일";
                        break;
                    case 5:
                        day = "토요일";
                        break;
                    default:
                        day = "일요일";
                        break;
                }
                String str = "매주 " + day;
                cycleFlag.setText(str);
                break;
            case 2: //매월
                String ss = "매달 " + al.get(position).date;
                cycleFlag.setText(ss);
                break;
        }
        Button unchecked = view.findViewById(R.id.unchecked);
        Button checked = view.findViewById(R.id.checked);
        if(selected.get(position) != null){
            unchecked.setVisibility(View.INVISIBLE);
            checked.setVisibility(View.VISIBLE);
        }
        return view;
    }

}

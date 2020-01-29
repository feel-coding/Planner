package my.study.planner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EveryPlanner {
    long id;
    String todo;
    int cycle;
    int date;
    int day;
    int category;

    public EveryPlanner(long id, String todo, int cycle, @Nullable int date, @Nullable int day, int category) {
        this.id = id;
        this.todo = todo;
        this.cycle = cycle;
        this.date = date;
        this.day = day;
        this.category = category;
    }
}
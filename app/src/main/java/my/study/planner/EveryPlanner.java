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

    @NonNull
    @Override
    public String toString() {
        return "id=" + id + ", 할일=" + todo + ", 주기=" + cycle + ", 매월 " + date +"일, " + "매주 " + day + "요일, " + "카테고리: "+ category;
    }
}
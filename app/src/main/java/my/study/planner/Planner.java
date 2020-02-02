package my.study.planner;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class Planner {
    long id;
    String todo;
    String date;
    int done;
    int category;
    long everyid;

    public Planner(long id, String todo, String date, int done, int category, long everyid) {
        this.id = id;
        this.todo = todo;
        this.date = date;
        this.done = done;
        this.category = category;
        this.everyid = everyid;
    }

    @NonNull
    @Override
    public String toString() {
        return "id: " + id + ", 할일: " + todo + ", 날짜: " + date + ", 다 했는지: " + done + ", 카테고리: " + category + ", every DB의 id" + everyid;
    }
}
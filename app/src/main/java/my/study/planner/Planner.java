package my.study.planner;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class Planner {
    long id;
    String todo;
    String date;
    int done;

    public Planner(long id, String todo, String date, int done) {
        this.id = id;
        this.todo = todo;
        this.date = date;
        this.done = done;
    }

    @NonNull
    @Override
    public String toString() {
        return id + ", " + todo + ", " + date + ", " + done;
    }
}
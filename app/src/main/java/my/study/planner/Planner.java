package my.study.planner;

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
}
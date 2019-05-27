package com.example.myapplication.model.models.realm;

import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.yandex_api.direction.Thread;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Flight extends RealmObject {
    @PrimaryKey
    int id;
    @Ignore
    Direction direction;
    Thread thread;
    Date date;

    public Flight() {
    }

    public Flight(Thread thread, Date date) {
        this.thread = thread;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}

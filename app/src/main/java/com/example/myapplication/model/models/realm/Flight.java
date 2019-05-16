package com.example.myapplication.model.models.realm;

import com.example.myapplication.model.models.yandex_api.direction.Thread;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Flight extends RealmObject {
    @PrimaryKey
    int id;
    Thread thread;
    RealmList<Station> stations;
    Date date;

    public Flight() {
    }

    public Flight(Thread thread, RealmList<Station> stations, Date date) {
        this.thread = thread;
        this.stations = stations;
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

    public RealmList<Station> getStations() {
        return stations;
    }

    public void setStations(RealmList<Station> stations) {
        this.stations = stations;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

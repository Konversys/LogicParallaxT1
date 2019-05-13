package com.example.myapplication.model.models.realm;

import java.time.LocalDateTime;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Station extends RealmObject {
    @PrimaryKey
    int id;
    String title;
    LocalDateTime arrival;
    LocalDateTime departure;
    String station_type_name;
    String code;
    int stop_time;
    long duration;
    long passed_time;

    public Station(int id, String title, LocalDateTime arrival, LocalDateTime departure, String station_type_name, String code, int stop_time, long duration) {
        this.id = id;
        this.title = title;
        this.arrival = arrival;
        this.departure = departure;
        this.station_type_name = station_type_name;
        this.code = code;
        this.stop_time = stop_time;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public String getStation_type_name() {
        return station_type_name;
    }

    public void setStation_type_name(String station_type_name) {
        this.station_type_name = station_type_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStop_time() {
        return stop_time;
    }

    public void setStop_time(int stop_time) {
        this.stop_time = stop_time;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getPassed_time() {
        return passed_time;
    }

    public void setPassed_time(long passed_time) {
        this.passed_time = passed_time;
    }
}

package com.example.myapplication.model.models.realm;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Station extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private Date arrival;
    private Date departure;
    private String station_type_name;
    private String code;
    private int stop_time;
    private long duration;
    private StationRatio ratio;
    private int time;

    public Station(int id, String title, String station_type_name, String code, int stop_time, long duration) {
        this.id = id;
        this.title = title;
        this.station_type_name = station_type_name;
        this.code = code;
        this.stop_time = stop_time;
        this.duration = duration;
    }

    public Station(int id, String title, Date arrival, Date departure, String station_type_name, String code, int stop_time, long duration) {
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

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
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

    public StationRatio getRatio() {
        return ratio;
    }

    public void setRatio(StationRatio ratio) {
        this.ratio = ratio;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

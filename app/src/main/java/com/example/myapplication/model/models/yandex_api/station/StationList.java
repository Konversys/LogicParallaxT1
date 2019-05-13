package com.example.myapplication.model.models.yandex_api.station;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class StationList {
    private String from;
    @Json(name = "uid")
    private String uid;
    @Json(name = "title")
    private String title;
    @Json(name = "start_time")
    private String startTime;
    @Json(name = "number")
    private String number;
    @Json(name = "short_title")
    private String shortTitle;
    @Json(name = "days")
    private String days;
    @Json(name = "stops")
    private List<Stop> stops = null;
    @Json(name = "start_date")
    private String startDate;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}

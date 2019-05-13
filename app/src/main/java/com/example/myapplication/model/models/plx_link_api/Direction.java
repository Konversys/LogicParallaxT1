package com.example.myapplication.model.models.plx_link_api;

import com.squareup.moshi.Json;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Direction extends RealmObject {
    @PrimaryKey
    @Json(name = "value")
    private String value;
    @Json(name = "name")
    private String name;
    @Json(name = "from")
    private String from;
    @Json(name = "to")
    private String to;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
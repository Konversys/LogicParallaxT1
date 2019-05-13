package com.example.myapplication.model.models.realm;

import java.time.LocalDateTime;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Checksum extends RealmObject {
    @PrimaryKey
    private String value;
    private LocalDateTime dateTime;

    public Checksum(String value, LocalDateTime dateTime) {
        this.value = value;
        this.dateTime = dateTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

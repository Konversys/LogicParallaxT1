package com.example.myapplication.model.models.realm;

public class CurrentStation {
    int station;
    CurrentPosition position;

    public CurrentStation(int station, CurrentPosition position) {
        this.station = station;
        this.position = position;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public CurrentPosition getPosition() {
        return position;
    }

    public void setPosition(CurrentPosition position) {
        this.position = position;
    }
}

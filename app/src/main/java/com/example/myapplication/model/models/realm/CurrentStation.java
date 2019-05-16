package com.example.myapplication.model.models.realm;

public class CurrentStation {
    int station;
    int position;

    public CurrentStation(int station, int position) {
        this.station = station;
        this.position = position;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

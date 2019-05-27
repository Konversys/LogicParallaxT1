package com.example.myapplication.model.logic;

import com.example.myapplication.model.models.realm.Station;
import com.example.myapplication.model.models.yandex_api.station.StationList;
import com.example.myapplication.model.models.yandex_api.station.Stop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.realm.RealmList;

public class YandexApiDataConverter {
    public static ArrayList<Station> StationYandexToStationRealm(StationList stationList) {
        ArrayList<Station> stations = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int count = 0;
        for (Stop station : stationList.getStops()) {
            Station item = new Station(
                    ++count,
                    station.getStation().getTitle(),
                    station.getStation().getStationTypeName(),
                    station.getStation().getCode(),
                    Integer.parseInt(station.getStopTime() == null ? "0" : station.getStopTime()),
                    station.getDuration());
            try {
                if (station.getArrival() != null)
                    item.setArrival(simpleDateFormat.parse(station.getArrival()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (station.getDeparture() != null)
                    item.setDeparture(simpleDateFormat.parse(station.getDeparture()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            stations.add(item);
        }
        return stations;
    }
}
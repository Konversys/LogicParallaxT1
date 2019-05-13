package com.example.myapplication.model.logic;

import com.example.myapplication.model.models.realm.Station;
import com.example.myapplication.model.models.yandex_api.station.StationList;
import com.example.myapplication.model.models.yandex_api.station.Stop;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class YandexApiDataConverter {
    static ArrayList<Station> StationYandexToStationRealm(StationList stationList) {
        ArrayList<Station> stations = new ArrayList<>();
        int count = 0;
        for (Stop station : stationList.getStops()) {
            stations.add(new Station(
                    count++,
                    station.getStation().getTitle(),
                    LocalDateTime.parse(station.getArrival()),
                    LocalDateTime.parse(station.getDeparture()),
                    station.getStation().getStationTypeName(),
                    station.getStation().getCode(),
                    Integer.parseInt(station.getStopTime()),
                    station.getDuration())
            );
        }
        return stations;
    }
}

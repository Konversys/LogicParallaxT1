package com.example.myapplication.model.logic;

import com.example.myapplication.model.models.realm.Station;
import com.example.myapplication.model.models.yandex_api.station.StationList;
import com.example.myapplication.model.models.yandex_api.station.Stop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.realm.RealmList;

public class YandexApiDataConverter {
    public static RealmList<Station> StationYandexToStationRealm(StationList stationList) {
        RealmList<Station> stations = new RealmList<>();
        int count = 0;
        //2019-05-19 11:13:00
        String date_pattern = "yyyy-MM-dd HH:mm:ss";
        for (Stop station : stationList.getStops()) {
            Station item = new Station(
                    count++,
                    station.getStation().getTitle(),
                    station.getStation().getStationTypeName(),
                    station.getStation().getCode(),
                    Integer.parseInt(station.getStopTime()),
                    station.getDuration());
            try {
                if (station.getArrival() != null)
                    item.setArrival(new SimpleDateFormat(date_pattern).parse(station.getArrival()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (station.getDeparture() != null)
                    item.setDeparture(new SimpleDateFormat(date_pattern).parse(station.getArrival()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            stations.add(item);
        }
        return stations;
    }
}
package com.example.myapplication.model.models.realm;

import com.example.myapplication.model.RealmHandler;

import java.util.ArrayList;
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
    private int ratio;

    public Station() {
    }

    public Station(String title, String station_type_name, String code, int stop_time, long duration) {
        this.title = title;
        this.station_type_name = station_type_name;
        this.code = code;
        this.stop_time = stop_time;
        this.duration = duration;
    }

    public Station(int id, String title, String station_type_name, String code, int stop_time, long duration) {
        this.id = id;
        this.title = title;
        this.station_type_name = station_type_name;
        this.code = code;
        this.stop_time = stop_time;
        this.duration = duration;
    }

    public Station(String title, Date arrival, Date departure, String station_type_name, String code, int stop_time, long duration) {
        this.title = title;
        this.arrival = arrival;
        this.departure = departure;
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

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public static CurrentStation getCurrentStation(ArrayList<Station> stations) {
        int left = 0;
        int right = stations.size() - 1;
        int mid;
        Date now = new Date();
        if (stations.get(0).getDeparture().after(now)) {
            mid = 0;
            return new CurrentStation(mid, CurrentPosition.BEFORE_START);
        } else if (stations.get(stations.size() - 1).getArrival().before(now)) {
            mid = stations.size() - 1;
            return new CurrentStation(mid, CurrentPosition.AFTER_END);
        } else {
            while (!(left >= right)) {
                mid = left + (right - left) / 2;
                if (stations.get(mid).getArrival() != null &&
                        stations.get(mid).getDeparture() != null &&
                        stations.get(mid).getArrival().before(now) &&
                        stations.get(mid).getDeparture().after(now)) {
                    return new CurrentStation(mid, CurrentPosition.STAY);
                } else if (stations.get(mid).getDeparture() != null &&
                        stations.get(mid + 1).getArrival() != null &&
                        stations.get(mid).getDeparture().before(now) &&
                        stations.get(mid + 1).getArrival().after(now)) {
                    return new CurrentStation(mid, CurrentPosition.BETWEEN_STATIONS);
                }
                if (stations.get(mid).getArrival().after(now))
                    right = mid;
                else
                    left = mid + 1;
            }
            return null;
        }
    }

    public static CurrentStation getCurrentStation() {
        return getCurrentStation(RealmHandler.GetStations());
    }

    public static ArrayList<Station> RefreshStationsRatio(ArrayList<Station> stations) {
        CurrentStation currentPosition = getCurrentStation(stations);
        int id = currentPosition.getStation();
        switch (currentPosition.getStation()) {
            case CurrentPosition.BETWEEN_STATIONS:
                stations.stream().filter(x -> x.getId() < id).forEach(x -> x.setRatio(StationRatio.BEHIND));
                stations.stream().filter(x -> x.getId() >= id).forEach(x -> x.setRatio(StationRatio.ARRIVAL));
                break;
            default:
                stations.stream().filter(x -> x.getId() < id).forEach(x -> x.setRatio(StationRatio.BEHIND));
                stations.stream().filter(x -> x.getId() > id).forEach(x -> x.setRatio(StationRatio.ARRIVAL));
                stations.stream().filter(x -> x.getId() == id).forEach(x -> x.setRatio(StationRatio.STAY));
                break;
        }
        return stations;
    }
}

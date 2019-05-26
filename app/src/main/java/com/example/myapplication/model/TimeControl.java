package com.example.myapplication.model;
import com.example.myapplication.model.models.realm.CurrentPosition;
import com.example.myapplication.model.models.realm.CurrentStation;
import com.example.myapplication.model.models.realm.Station;
import com.example.myapplication.model.models.realm.StationRatio;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class TimeControl {
    private static Timer timer;
    private static Realm realm;

    public void StartTimer(){
        realm = realm.getDefaultInstance();
        int period = 1000;
        if (timer == null)
            timer = new Timer();
        timer.schedule(new TimeDirectionStep(), 0, period);
    }

    public void StopTimer(){
        timer.cancel();
        realm.close();
    }

    class TimeDirectionStep extends TimerTask {
        @Override
        public void run() {
            realm.executeTransaction(realm -> {
                RealmQuery<Station> stations_query = realm.where(Station.class);
                RealmResults<Station> stations = stations_query.findAll();
                CurrentStation currentPosition = getCurrentStation(stations);
                if (currentPosition == null)
                    return;
                if (currentPosition.getPosition() == CurrentPosition.BETWEEN_STATIONS) {
                    stations.where().lessThan("id", currentPosition.getStation()).findAll().forEach(x -> x.setRatio(StationRatio.ARRIVAL));
                    stations.where().greaterThanOrEqualTo("id", currentPosition.getStation()).findAll().forEach(x -> x.setRatio(StationRatio.BEHIND));
                }
                else{
                    stations.where().lessThan("id", currentPosition.getStation()).findAll().forEach(x -> x.setRatio(StationRatio.ARRIVAL));
                    stations.where().greaterThan("id", currentPosition.getStation()).findAll().forEach(x -> x.setRatio(StationRatio.BEHIND));
                    stations.where().equalTo("id", currentPosition.getStation()).findAll().forEach(x -> x.setRatio(StationRatio.STAY));
                }
            });
        }
    }

    public static CurrentStation getCurrentStation()
    {
        RealmQuery<Station> stations_query = realm.where(Station.class);
        return getCurrentStation(stations_query.findAll());
    }

    public static CurrentStation getCurrentStation(RealmResults<Station> stations)
    {
        int left = 0;
        int right = stations.size() - 1;
        int mid;
        CurrentPosition currentPos;
        Date now = new Date();
        if (stations.get(0).getDeparture().before(now)) {
            mid = 0;
            return new CurrentStation(mid, CurrentPosition.BEFORE_START);
        }
        else if (stations.get(stations.size()-1).getArrival().after(now)) {
            mid = stations.size() - 1;
            return new CurrentStation(mid, CurrentPosition.AFTER_END);
        }
        else {
            while (!(left >= right)) {
                mid = left + (right - left) / 2;

                if (stations.get(mid).getArrival() != null &&
                        stations.get(mid).getDeparture() != null &&
                        stations.get(mid).getArrival().after(now) &&
                        stations.get(mid).getDeparture().before(now)) {
                    return new CurrentStation(mid, CurrentPosition.STAY);
                } else if (stations.get(mid).getDeparture() != null &&
                        stations.get(mid + 1).getArrival() != null &&
                        stations.get(mid).getDeparture().after(now) &&
                        stations.get(mid + 1).getArrival().before(now)) {
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
}
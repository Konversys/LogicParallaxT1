package com.example.myapplication.model;
import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.realm.Station;

import java.util.ArrayList;
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

    enum CurrentPosition{
        BeforeStart,
        AfterEnd,
        BetweenStations,
        Stay
    }

    class TimeDirectionStep extends TimerTask {
        @Override
        public void run() {
            realm.executeTransaction(realm -> {
                RealmQuery<Station> stations_query = realm.where(Station.class);
                int id = -1;
                CurrentPosition currentPos;
                Date now = new Date();
                RealmResults<Station> stations = stations_query.findAll();

                if (stations.get(0).getDeparture().before(now)) {
                    id = 0;
                    currentPos = CurrentPosition.BeforeStart;
                }
                else if (stations.get(stations.size()-1).getArrival().after(now)) {
                    id = stations.size() - 1;
                    currentPos = CurrentPosition.AfterEnd;
                }
                else {
                    for (int i = 1; i < stations.size()-1; i++) {
                        if (stations.get(i).getArrival() != null &&
                                stations.get(i).getDeparture() != null &&
                                stations.get(i).getArrival().after(now) &&
                                stations.get(i).getDeparture().before(now)) {
                            id = i;
                            currentPos = CurrentPosition.Stay;
                            break;
                        }

                        if (stations.get(i).getDeparture() != null &&
                                stations.get(i+1).getArrival() != null &&
                                stations.get(i).getDeparture().after(now) &&
                                stations.get(i + 1).getArrival().before(now)) {
                            id = i;
                            currentPos = CurrentPosition.BetweenStations;
                            break;
                        }
                    }
                }
            });
        }
    }
}

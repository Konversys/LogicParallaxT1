package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.model.logic.Tools;
import com.example.myapplication.model.models.realm.CurrentPosition;
import com.example.myapplication.model.models.realm.CurrentStation;
import com.example.myapplication.model.models.realm.Station;
import com.example.myapplication.model.models.realm.StationRatio;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AdapterStations extends ArrayAdapter<Station> {

    private static final String BEHIND = "Пройдено";
    private static final String STAY = "До отправки ";
    private static final String ARRIVAL = "До прибытия ";

    private ArrayList<Station> stations;
    private ArrayList<ViewHolder> lstHolders;
    private Handler handler = new Handler();

    private Context ctx;
    private LayoutInflater lf;

    public AdapterStations(Context ctx, ArrayList<Station> stations) {
        super(ctx, 0, stations);
        this.ctx = ctx;
        this.stations = stations;
        lstHolders = new ArrayList<>();
        lf = lf.from(ctx);
        startUpdateTimer();
    }

    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                for (ViewHolder holder : lstHolders) {
                    holder.updateTimeRemaining();
                }
            }
        }
    };

    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(updateRemainingTimeRunnable);
            }
        }, 0, 1000);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            holder = new ViewHolder();
            LayoutInflater inflater;
            v = lf.inflate(R.layout.item_station, parent, false);
            holder.number = (TextView) v.findViewById(R.id.ItemStationNumber);
            holder.station = (TextView) v.findViewById(R.id.ItemStationStation);
            holder.time = (TextView) v.findViewById(R.id.ItemStationTime);
            holder.arrival = (TextView) v.findViewById(R.id.ItemStationArrival);
            holder.stay = (TextView) v.findViewById(R.id.ItemStationStay);
            holder.departure = (TextView) v.findViewById(R.id.ItemStationDeparture);
            v.setTag(holder);
            synchronized (lstHolders) {
                lstHolders.add(holder);
            }
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.setData(getItem(position));
        return v;
    }

    private class ViewHolder {
        public View lyt_parent;
        public TextView number;
        public TextView station;
        public TextView time;
        public TextView arrival;
        public TextView stay;
        public TextView departure;

        Station value;

        public void setData(Station item) {
            value = item;
            number.setText(String.valueOf(item.getId()));
            station.setText(item.getTitle());
            arrival.setText(item.getArrival() == null ? "Станция отправления" : Tools.getFormattedStringWithTime(item.getArrival()));
            stay.setText(item.getStop_time() / 60 + " мин");
            departure.setText(item.getDeparture() == null ? "Конечная станция" : Tools.getFormattedStringWithTime(item.getDeparture()));
            updateTimeRemaining();
        }

        public void updateTimeRemaining() {
            Date now = new Date();
            RefreshStationsRatio();
            switch (value.getRatio()) {
                case StationRatio.BEHIND:
                    time.setTextColor(Color.RED);
                    time.setText(BEHIND);
                    break;
                case StationRatio.STAY:
                    time.setTextColor(Color.YELLOW);
                    time.setText(Tools.getStringDifferenceOfStationDates(
                            Tools.getDifferenceOfStationDates(now, value.getDeparture())));
                    break;
                case StationRatio.ARRIVAL:
                    time.setTextColor(Color.GREEN);
                    time.setText(Tools.getStringDifferenceOfStationDates(
                            Tools.getDifferenceOfStationDates(now, value.getArrival())));
                    break;
                default:
                    time.setText("Неизвестно");
                    break;
            }
        }
    }

    public void RefreshStationsRatio() {
        CurrentStation currentPosition = getCurrentStation();
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
    }

    public CurrentStation getCurrentStation() {
        int left = 0;
        int right = stations.size() - 1;
        int mid;
        //  если объект класса Date содержит более раннюю дату, чем указано в параметре, то возвращается true
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
}
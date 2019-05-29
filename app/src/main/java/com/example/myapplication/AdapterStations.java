package com.example.myapplication;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.logic.Tools;
import com.example.myapplication.model.models.realm.Station;
import com.example.myapplication.model.models.realm.StationRatio;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Date;

public class AdapterStations extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String BEHIND = "Пройдено";
    private static final String STAY = "До отправки ";
    private static final String ARRIVAL = "До прибытия ";

    private ArrayList<Station> stations;

    private Context ctx;

    public AdapterStations(Context ctx, ArrayList<Station> stations) {
        this.ctx = ctx;
        this.stations = stations;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public View lyt_parent;
        public TextView number;
        public TextView station;
        public TextView time;
        public TextView arrival;
        public TextView stay;
        public TextView departure;

        public OriginalViewHolder(View v) {
            super(v);
            lyt_parent = (View) v.findViewById(R.id.ItemStationLytParent);
            number = (TextView) v.findViewById(R.id.ItemStationNumber);
            station = (TextView) v.findViewById(R.id.ItemStationStation);
            time = (TextView) v.findViewById(R.id.ItemStationTime);
            arrival = (TextView) v.findViewById(R.id.ItemStationArrival);
            stay = (TextView) v.findViewById(R.id.ItemStationStay);
            departure = (TextView) v.findViewById(R.id.ItemStationDeparture);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            if (holder.timer != null) {
                holder.timer.cancel();
            }
            holder.timer = new CountDownTimer(expiryTime, 500) {
        ...
            }.start();

            Station station = stations.get(position);

            view.number.setText(String.valueOf(station.getId()));
            view.station.setText(station.getTitle());
            Date now = new Date();
            switch (station.getRatio()) {
                case StationRatio.BEHIND:
                    view.time.setText(BEHIND);
                    break;
                case StationRatio.STAY:
                    view.time.setText(STAY + Tools.getStringDifferenceOfStationDates(
                            Tools.getDifferenceOfStationDates(now, station.getDeparture())));
                    break;
                case StationRatio.ARRIVAL:
                    view.time.setText(ARRIVAL + Tools.getStringDifferenceOfStationDates(
                            Tools.getDifferenceOfStationDates(now, station.getArrival())));
                    break;
                default:
                    view.time.setText("Неизвестно");
                    break;
            }
            view.arrival.setText(station.getArrival() == null ? "Станция отправления" : Tools.getFormattedStringWithTime(station.getArrival()));
            view.stay.setText(station.getStop_time() / 60 + " мин");
            view.departure.setText(station.getDeparture() == null ? "Конечная станция" : Tools.getFormattedStringWithTime(station.getDeparture()));
        }
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    class ViewHolder {
        Station station;

        public void setData(Station item) {
            station = item;
            tvProduct.setText(item.name);
            updateTimeRemaining(System.currentTimeMillis());
        }

        public void updateTimeRemaining(long currentTime) {
            long timeDiff = mProduct.expirationTime - currentTime;
            if (timeDiff > 0) {
                int seconds = (int) (timeDiff / 1000) % 60;
                int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
                int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
                tvTimeRemaining.setText(hours + " hrs " + minutes + " mins " + seconds + " sec");
            } else {
                tvTimeRemaining.setText("Expired!!");
            }
        }
    }
}

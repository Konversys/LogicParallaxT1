package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.logic.Tools;
import com.example.myapplication.model.models.realm.Station;

import java.util.ArrayList;

public class AdapterStations extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Station> stations;

    private Context ctx;

    public AdapterStations(Context ctx) {
        this.ctx = ctx;
        this.stations = RealmHandler.GetStations();
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

            Station station = stations.get(position);

            view.number.setText(String.valueOf(station.getId()));
            view.station.setText(station.getTitle());
            view.time.setText(String.valueOf(station.getTime()));
            view.arrival.setText(station.getArrival() == null ? "Станция отправления" : Tools.getFormattedStringWithTime(station.getArrival()));
            view.stay.setText(station.getStop_time() / 60 + " мин");
            view.departure.setText(station.getDeparture() == null ? "Конечная станция" : Tools.getFormattedStringWithTime(station.getDeparture()));
        }
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }
}

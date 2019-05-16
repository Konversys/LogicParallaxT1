package com.example.myapplication;

import android.content.Context;
import android.provider.Contacts;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.model.models.realm.Flight;
import com.example.myapplication.model.models.realm.Station;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class AdapterStations extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Station> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Station obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterStations(Context context, List<Station> items) {
        this.items = items;
        ctx = context;
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
            lyt_parent = v.findViewById(R.id.ItemStationLytParent);
            number = v.findViewById(R.id.ItemStationNumber);
            station = v.findViewById(R.id.ItemStationStation);
            time = v.findViewById(R.id.ItemStationTime);
            arrival = v.findViewById(R.id.ItemStationArrival);
            stay = v.findViewById(R.id.ItemStationStay);
            departure = v.findViewById(R.id.ItemStationDeparture);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Station p = items.get(position);
            view.number.setText(p.getId()+1);
            view.station.setText(p.getTitle());
            view.time.setText(Integer.toString(p.getTime()));
            view.arrival.setText(p.getArrival().toString());
            view.stay.setText(Double.toString(p.getDuration()/60));
            view.departure.setText(p.getDeparture().toString());
            view.lyt_parent.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, items.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

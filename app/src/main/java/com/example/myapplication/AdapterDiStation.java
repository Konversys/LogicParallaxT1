package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.realm.Station;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class AdapterDiStation extends ArrayAdapter<Station> {

    private ArrayList<Station> stations;

    public AdapterDiStation(Context context) {
        super(context, 0);
        
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return stationsFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_di_station
                    , parent, false);
        }
        TextView item = convertView.findViewById(R.id.ItemDiStationStation);
        Station station = getItem(position);
        if (station != null) {
            item.setText(station.getTitle());
        }
        return convertView;
    }

    private Filter stationsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null) {
                stations = RealmHandler.GetStations();
            } else {
                stations = RealmHandler.GetStationsByName(constraint.toString().trim());
            }
            results.values = stations;
            results.count = stations.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Station) resultValue).getTitle();
        }
    };
}
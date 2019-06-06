package com.example.myapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.realm.Coupe;
import com.example.myapplication.model.models.realm.CurrentStation;
import com.example.myapplication.model.models.realm.Place;
import com.example.myapplication.model.models.realm.Station;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class AdapterWagonCoupe extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Coupe> items = new ArrayList<>();

    public void ResetItems() {
        this.items = new ArrayList<>(RealmHandler.GetWagon().getCoupes());
    }

    private Context ctx;
    private AdapterWagonCoupe.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Coupe obj, int position);
    }

    public void setOnItemClickListener(final AdapterWagonCoupe.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterWagonCoupe(Context context) {
        ResetItems();
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView coupe;
        public Button lt;
        public Button rt;
        public Button lb;
        public Button rb;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            coupe = (TextView) v.findViewById(R.id.ItemCoupeKypeNumber);
            lt = (Button) v.findViewById(R.id.ItemCoupeKypeLT);
            rt = (Button) v.findViewById(R.id.ItemCoupeKypeRT);
            lb = (Button) v.findViewById(R.id.ItemCoupeKypeLB);
            rb = (Button) v.findViewById(R.id.ItemCoupeKypeRB);
            lyt_parent = (View) v.findViewById(R.id.ItemCoupeKypeLyt);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupe_kype, parent, false);
        vh = new AdapterWagonCoupe.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterWagonCoupe.OriginalViewHolder) {
            AdapterWagonCoupe.OriginalViewHolder view = (AdapterWagonCoupe.OriginalViewHolder) holder;
            PlaceDialog placeDialog = new PlaceDialog(ctx, this);
            Station station = RealmHandler.GetStationsByID(Station.getCurrentStation().getStation()+1);
            int stationId = station.getId();
            boolean isEnd = false;
            if (station == null){
                isEnd = true;
            }
            Coupe item = items.get(position);
            view.coupe.setText(String.valueOf(item.getNumber()));
            if (item.getPlaceLB().isTaken()) {
                if (item.getPlaceLB().getTo().getId() == stationId){
                    view.lb.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                else {
                    view.lb.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                }
                view.lb.setText(item.getPlaceLB().getNumber() + " " + item.getPlaceLB().getTo().getTitle());
            } else {
                view.lb.setBackgroundResource(R.color.amber_50);
                view.lb.setText(String.valueOf(item.getPlaceLB().getNumber()));
            }

            if (item.getPlaceRB().isTaken()) {
                if (item.getPlaceRB().getTo().getId() == stationId){
                    view.rb.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                else {
                    view.rb.setBackgroundResource(R.color.amber_50);
                }
                view.rb.setText(item.getPlaceRB().getNumber() + " " + item.getPlaceRB().getTo().getTitle());
            } else {
                view.rb.setText(String.valueOf(item.getPlaceRB().getNumber()));
                view.rb.setBackgroundResource(R.color.amber_50);
            }

            if (item.getPlaceLT().isTaken()) {
                if (item.getPlaceLT().getTo().getId() == stationId){
                    view.lt.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                else {
                    view.lt.setBackgroundResource(R.color.amber_50);
                }
                view.lt.setText(item.getPlaceLT().getNumber() + " " + item.getPlaceLT().getTo().getTitle());
            } else {
                view.lt.setBackgroundResource(R.color.amber_50);
                view.lt.setText(String.valueOf(item.getPlaceLT().getNumber()));
            }

            if (item.getPlaceRT().isTaken()) {
                int placeStationId = item.getPlaceRT().getTo().getId();
                if (placeStationId == stationId){
                    // След станция
                    view.rt.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                else if(placeStationId > stationId) {
                    // Проехал
                    view.rt.setBackgroundResource(R.color.amber_50);
                } else {
                    // Красивчик
                }
                view.rt.setText(item.getPlaceRT().getNumber() + " " + item.getPlaceRT().getTo().getTitle());
            } else {
                view.rt.setBackgroundResource(R.color.amber_50);
                view.rt.setText(String.valueOf(item.getPlaceRT().getNumber()));
            }

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

            view.lb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeDialog.showDialog(item.getPlaceLB());
                }
            });
            view.lt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeDialog.showDialog(item.getPlaceLT());
                }
            });
            view.rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeDialog.showDialog(item.getPlaceRB());
                }
            });
            view.rt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeDialog.showDialog(item.getPlaceRT());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

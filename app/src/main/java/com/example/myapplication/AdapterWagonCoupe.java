package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.realm.Coupe;
import com.example.myapplication.model.models.realm.Place;

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

    public AdapterWagonCoupe(Context context, List<Coupe> coupes) {
        this.items = coupes;
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

            Coupe item = items.get(position);
            view.coupe.setText(String.valueOf(item.getNumber()));
            if (item.getPlaceLB().isTaken())
                view.lb.setText(item.getPlaceLB().getTo().getTitle());
            else
                view.lb.setText(String.valueOf(item.getPlaceLB().getNumber()));

            if (item.getPlaceRB().isTaken())
                view.rb.setText(item.getPlaceRB().getTo().getTitle());
            else
                view.rb.setText(String.valueOf(item.getPlaceRB().getNumber()));

            if (item.getPlaceLT().isTaken())
                view.lt.setText(item.getPlaceLT().getTo().getTitle());
            else
                view.lt.setText(String.valueOf(item.getPlaceLT().getNumber()));

            if (item.getPlaceRB().isTaken())
                view.rt.setText(item.getPlaceRT().getTo().getTitle());
            else
                view.rt.setText(String.valueOf(item.getPlaceRT().getNumber()));

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

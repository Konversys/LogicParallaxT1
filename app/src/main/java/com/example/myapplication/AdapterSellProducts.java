package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.models.realm.SellProduct;

import java.util.ArrayList;
import java.util.List;

public class AdapterSellProducts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SellProduct> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, SellProduct obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterSellProducts(Context context, List<SellProduct> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView number;
        public TextView price;
        public TextView count;
        public TextView about;
        public TextView selled;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            number = (TextView) v.findViewById(R.id.ItemSellNumber);
            price = (TextView) v.findViewById(R.id.ItemSellPrice);
            count = (TextView) v.findViewById(R.id.ItemSellCount);
            about = (TextView) v.findViewById(R.id.ItemSellAbout);
            selled = (TextView) v.findViewById(R.id.ItemSellSelled);
            lyt_parent = (View) v.findViewById(R.id.ItemSellLyt);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sell, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            SellProduct item = items.get(position);
            view.number.setText(item.getId());
            view.price.setText(String.valueOf(item.getPrice()));
            view.about.setText(item.getAbout());
            view.count.setText(item.getCount());
            view.selled.setText(item.getSold());
            view.selled.setText("Продано " + item.getSold() + " из " + item.getTotal());
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

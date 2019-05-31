package com.example.myapplication.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AdapterProducts;
import com.example.myapplication.model.models.plx_link_api.Product;

import java.util.ArrayList;
import java.util.List;

public class AdapterProductsRV extends BaseAdapter implements Filterable {

    private final AdapterProducts mAdapter;

    public AdapterProductsRV(AdapterProducts adapter) {
        mAdapter = adapter;
    }

    @Override
    public Filter getFilter() {
        return productFilter;
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Product> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                suggestions.addAll(mAdapter.items_swiped);
            }
            else {
                String line = constraint.toString().toLowerCase();
                for (Product item: mAdapter.items_swiped){
                    if(item.getTitle().toLowerCase().contains(line) ||
                            item.getAbout().toLowerCase().contains(line) ||
                            item.getCategory().toLowerCase().contains(line) ||
                            String.valueOf(item.getPrice()).contains(line)){
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAdapter.items_swiped.clear();
            mAdapter.items_swiped.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getCount() {
        return mAdapter.items_swiped.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return mAdapter.getItemId(position);
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecyclerView.ViewHolder holder;
        if (convertView == null) {
            holder = mAdapter.createViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (RecyclerView.ViewHolder) convertView.getTag();
        }
        mAdapter.bindViewHolder(holder, position);
        return holder.itemView;
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(position);
    }
}

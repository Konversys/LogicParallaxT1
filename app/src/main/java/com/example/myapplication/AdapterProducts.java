package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.models.plx_link_api.Product;

import java.util.ArrayList;

public class AdapterProducts extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements SwipeItemTouchHelper.SwipeHelperAdapter {

    private ArrayList<Product> items = new ArrayList<>();
    private ArrayList<Product> items_swiped = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Product obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterProducts(Context context, ArrayList<Product> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {
        public TextView number;
        public TextView title;
        public TextView price;
        public TextView category;
        public TextView count;
        public TextView about;
        public ImageView image;

        public Button bt_undo;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.ItemProductImage);
            number = (TextView) v.findViewById(R.id.ItemProductNumber);
            title = (TextView) v.findViewById(R.id.ItemProductTitle);
            price = (TextView) v.findViewById(R.id.ItemProductPrice);
            category = (TextView) v.findViewById(R.id.ItemProductCategory);
            count = (TextView) v.findViewById(R.id.ItemProductCount);
            about = (TextView) v.findViewById(R.id.ItemProductAbout);
            bt_undo = (Button) v.findViewById(R.id.bt_undo);
            lyt_parent = (View) v.findViewById(R.id.ItemProductLytParent);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ctx.getResources().getColor(R.color.grey_5));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final Product product = items.get(position);
            view.number.setText(String.valueOf(product.getId()));
            if (product.getTitle() != null)
                view.title.setText(product.getTitle());
            else
                view.title.setText("");

            if (product.getCount() != null)
                view.count.setText(product.getCount());
            else
                view.count.setText("");

            if (product.getCategory() != null)
                view.category.setText(product.getCategory());
            else
                view.category.setText("");

            if (product.getAbout() != null)
                view.about.setText(product.getAbout());
            else
                view.about.setText("");

            view.price.setText(String.valueOf(product.getPrice()));

            //Tools.displayImageOriginal(ctx, view.image, p.image);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

            view.bt_undo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.get(position).setSwipe(false);
                    items_swiped.remove(items.get(position));
                    notifyItemChanged(position);
                }
            });

            if (product.isSwipe()) {
                view.lyt_parent.setVisibility(View.GONE);
            } else {
                view.lyt_parent.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                for (Product s : items_swiped) {
                    int index_removed = items.indexOf(s);
                    if (index_removed != -1) {
                        items.remove(index_removed);
                        notifyItemRemoved(index_removed);
                    }
                }
                items_swiped.clear();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemDismiss(int position) {
        // handle when double swipe
        if (items.get(position).isSwipe()) {
            items_swiped.remove(items.get(position));
            items.remove(position);
            notifyItemRemoved(position);
        return;
        }

        items.get(position).setSwipe(true);
        items_swiped.add(items.get(position));
        notifyItemChanged(position);
    }

}
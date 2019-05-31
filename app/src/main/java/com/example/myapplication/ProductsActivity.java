package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;

import com.example.myapplication.model.AdapterProductsRV;
import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.plx_link_api.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ProductsActivity extends AppCompatActivity {

    RecyclerView productsView;
    AutoCompleteTextView productSearch;
    AdapterProducts mAdapter;
    ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        productSearch = (AutoCompleteTextView) findViewById(R.id.AcProductsSearch);
        initProductList();
    }

    private void initProductList() {
        productsView = (RecyclerView) findViewById(R.id.AcProductsRecycler);
        productsView.setLayoutManager(new LinearLayoutManager(this));
        productsView.setHasFixedSize(true);

        ArrayList<Product> products = RealmHandler.GetProducts();

        //set data and list adapter
        mAdapter = new AdapterProducts(this, products);
        productsView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SwipeItemTouchHelper(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(productsView);
        productSearch.setAdapter(new AdapterProductsRV(mAdapter));
    }

    private void initAutocompete() {

    }
}

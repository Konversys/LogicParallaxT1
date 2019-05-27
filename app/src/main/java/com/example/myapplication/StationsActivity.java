package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.RealmHandler;


public class StationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterStations mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);

        //initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Basic");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.AcStationsStationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        mAdapter = new AdapterStations(this);
        recyclerView.setAdapter(mAdapter);
    }
}
package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.StationAdapterTimeControl;
import com.example.myapplication.model.models.realm.Station;

import java.util.ArrayList;

public class StationsActivity extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private static AdapterStations mAdapter;
    private static ArrayList<Station> stations;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        context = this;
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

        stations = RealmHandler.GetStations();
        //set data and list adapter
        mAdapter = new AdapterStations(this, stations);
        recyclerView.setAdapter(mAdapter);
        UpdateStatonsTime();
    }

    private void UpdateStatonsTime(){
        StationAdapterTimeControl stationTimeControl = new StationAdapterTimeControl(stations);
        stationTimeControl.StartTimer();
    }

    public static void StationsDataSetChanged(){
        mAdapter = new AdapterStations(context, stations);
    }
}
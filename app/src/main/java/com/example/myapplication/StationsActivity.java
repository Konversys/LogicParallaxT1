package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.realm.Station;

import java.util.ArrayList;

public class StationsActivity extends AppCompatActivity {

    private static ListView listView;
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
        stations = RealmHandler.GetStations();

        listView = (ListView) findViewById(R.id.AcStationsStationList);
        mAdapter = new AdapterStations(this, stations);
        listView.setAdapter(mAdapter);
    }
}
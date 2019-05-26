package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.web.ApiInstanse;
import com.example.myapplication.model.web.ApiPlxLink;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDirection extends AppCompatActivity {

    private TextView status;
    private AutoCompleteTextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_direction);
        status = findViewById(R.id.AcSelectDirectionStatus);
        search = findViewById(R.id.AcSelectDirectionSearch);
        search.setAdapter(new AdapterDirection(this));
        search.setOnItemClickListener((parent, view, position, id) -> {
            Object item = parent.getItemAtPosition(position);
            if (item instanceof Direction){
                Direction direction =(Direction) item;
                status.setText(direction.getValue());
            }
        });
    }
}

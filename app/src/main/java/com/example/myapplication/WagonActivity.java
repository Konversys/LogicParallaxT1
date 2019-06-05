package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.realm.Coupe;
import com.example.myapplication.model.models.realm.SellProduct;
import com.example.myapplication.model.models.realm.Wagon;

import java.util.ArrayList;
import java.util.List;

public class WagonActivity extends AppCompatActivity {

    RecyclerView coupesView;
    AdapterWagonCoupe adapterWagonCoupe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wagon);
        initWagon();
    }

    private void initWagon(){
        coupesView = (RecyclerView) findViewById(R.id.AcWagonCoupes);
        coupesView.setLayoutManager(new LinearLayoutManager(this));
        coupesView.setHasFixedSize(true);

        //if (RealmHandler.GetWagon() == null)
            RealmHandler.CleanSetWagon(9, 6, 51064);
        Wagon wagon = RealmHandler.GetWagon();
        List<Coupe> coupeList = new ArrayList<>(RealmHandler.GetWagon().getCoupes());

        adapterWagonCoupe = new AdapterWagonCoupe(this, coupeList);
        coupesView.setAdapter(adapterWagonCoupe);
    }
}

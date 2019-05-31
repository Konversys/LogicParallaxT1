package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.plx_link_api.Product;
import com.example.myapplication.model.models.yandex_api.direction.Segment;

import com.example.myapplication.model.web.ApiInstanse;
import com.example.myapplication.model.web.ApiPlxLink;
import com.example.myapplication.model.web.ApiYandex;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Direction> directions;
    static TextView status;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        context = this;
        setContentView(R.layout.activity_main);
        status = findViewById(R.id.status);
        ApiInstanse.getPlxLinkApi().getValidDirections().enqueue(new Callback<List<Direction>>() {
            @Override
            public void onResponse(Call<List<Direction>> call, Response<List<Direction>> response) {
                status.setText("Успешно");
                RealmHandler.CleanSaveDirections(response.body());
                Intent intent = new Intent(context, SelectDirection.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<Direction>> call, Throwable t) {
            }
        });

        ApiInstanse.getPlxLinkApi().getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                RealmHandler.SaveProducts(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });

    }
}

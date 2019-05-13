package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.yandex_api.direction.DirectionYandex;
import com.example.myapplication.model.models.yandex_api.direction.Segment;
import com.example.myapplication.model.models.yandex_api.station.StationList;
import com.example.myapplication.model.models.yandex_api.station.Stop;
import com.example.myapplication.model.web.ApiInstanse;
import com.example.myapplication.model.web.ApiPlxLink;
import com.example.myapplication.model.web.ApiYandex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Direction> directions;
    static ArrayList<Segment> directionsYandex;
    static ArrayList<Stop> stationLists;
    static TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        status = findViewById(R.id.status);
        ApiPlxLink api_link = ApiInstanse.getPlxLinkApi();
        ApiYandex api_yandex = ApiInstanse.getYandexApi();
        api_link.getValidDirections().enqueue(new Callback<List<Direction>>() {
            @Override
            public void onResponse(Call<List<Direction>> call, Response<List<Direction>> response) {
                directions = new ArrayList<>(response.body());
                status.setText("Успешно");
                GetYandexDir();
            }

            @Override
            public void onFailure(Call<List<Direction>> call, Throwable t) {
                status.setText("Успешно");
            }
        });
        status.setText("Загрузка");
    }

    static void GetYandexDir()
    {
        ApiYandex api_yandex = ApiInstanse.getYandexApi();
        String date = "2019-05-13";
        api_yandex.getDirections(directions.get(0).getFrom(), directions.get(0).getTo(), date)
                .enqueue(
                        new Callback<DirectionYandex>() {
                            @Override
                            public void onResponse(Call<DirectionYandex> call, Response<DirectionYandex> response) {
                                directionsYandex = new ArrayList<>(response.body().getSegments().stream().filter(
                                        x -> x.getThread().getNumber().equals(directions.get(0).getValue())
                                ).collect(Collectors.toList()));
                                status.setText("Успешно ya");
                                GetYandexStations(directionsYandex.get(0).getThread().getUid());
                            }

                            @Override
                            public void onFailure(Call<DirectionYandex> call, Throwable t) {
                                status.setText("Ошибка ya");
                            }
                        }
                );
    }

    static void GetYandexStations(String uid)
    {
        ApiYandex api_yandex = ApiInstanse.getYandexApi();
        String date = "2019-05-13";
        api_yandex.getStations(uid, date).enqueue(
                new Callback<StationList>() {
                    @Override
                    public void onResponse(Call<StationList> call, Response<StationList> response) {
                        stationLists = new ArrayList<>(response.body().getStops());
                        status.setText("Успешно st");
                    }

                    @Override
                    public void onFailure(Call<StationList> call, Throwable t) {
                        status.setText("Ошибка st");
                    }
                }
        );
    }
}

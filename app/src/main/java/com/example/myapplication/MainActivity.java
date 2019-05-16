package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.model.logic.Tools;
import com.example.myapplication.model.logic.YandexApiDataConverter;
import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.realm.Flight;
import com.example.myapplication.model.models.yandex_api.direction.DirectionYandex;
import com.example.myapplication.model.models.yandex_api.direction.Segment;
import com.example.myapplication.model.models.yandex_api.direction.Thread;
import com.example.myapplication.model.models.yandex_api.station.StationList;
import com.example.myapplication.model.models.yandex_api.station.Stop;
import com.example.myapplication.model.web.ApiInstanse;
import com.example.myapplication.model.web.ApiPlxLink;
import com.example.myapplication.model.web.ApiYandex;
import com.facebook.stetho.Stetho;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        Stetho.initializeWithDefaults(this);

        setContentView(R.layout.activity_main);
        status = findViewById(R.id.status);
        ApiYandex api_yandex = ApiInstanse.getYandexApi();
        ApiPlxLink api_link = ApiInstanse.getPlxLinkApi();
        //api_link.getValidDirections().enqueue(new Callback<List<Direction>>() {
        //    @Override
        //    public void onResponse(Call<List<Direction>> call, Response<List<Direction>> response) {
        //        directions = new ArrayList<>(response.body());
        //        status.setText("Успешно");
        //        Realm realm = Realm.getDefaultInstance();
        //        realm.beginTransaction();
        //        realm.where(Direction.class).findAll().deleteAllFromRealm();
        //        realm.copyToRealm(directions);
        //        realm.commitTransaction();
        //        realm.close();
        //        GetYandexDir();
        //    }

        //    @Override
        //    public void onFailure(Call<List<Direction>> call, Throwable t) {
        //        status.setText("Успешно");
        //    }
        //});
        Intent intent = new Intent(this, SelectDirection.class);
        startActivity(intent);
        //status.setText("Загрузка");
    }

    static void GetYandexDir() {
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
                                GetYandexStations(directionsYandex.get(0).getThread());
                            }

                            @Override
                            public void onFailure(Call<DirectionYandex> call, Throwable t) {
                                status.setText("Ошибка ya");
                            }
                        }
                );
    }

    static void GetYandexStations(Thread thread) {
        String uid = thread.getUid();
        ApiYandex api_yandex = ApiInstanse.getYandexApi();
        String date = "2019-05-13";
        api_yandex.getStations(uid, date).enqueue(
                new Callback<StationList>() {
                    @Override
                    public void onResponse(Call<StationList> call, Response<StationList> response) {
                        status.setText("Успешно st");
                        try {
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            realm.copyToRealm(new Flight(thread,
                                    YandexApiDataConverter.StationYandexToStationRealm(response.body()),
                                    Tools.StrToDate(date)));
                            realm.commitTransaction();
                            realm.close();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<StationList> call, Throwable t) {
                        status.setText("Ошибка st");
                    }
                }
        );
    }
}

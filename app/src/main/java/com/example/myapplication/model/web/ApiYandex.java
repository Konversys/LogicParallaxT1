package com.example.myapplication.model.web;

import com.example.myapplication.model.models.yandex_api.direction.DirectionYandex;
import com.example.myapplication.model.models.yandex_api.station.StationList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiYandex {

    @GET(WebConsts.YANDEX_API_DIRECTIONS)
    Call<DirectionYandex> getDirections(@Query("from") String from, @Query("to") String to, @Query("date") String date);

    @GET(WebConsts.YANDEX_API_STATIONS)
    Call<StationList> getStations(@Query("uid") String uid, @Query("date") String date);
}

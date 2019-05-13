package com.example.myapplication.model.models.yandex_api.station;

import com.squareup.moshi.Json;

public class Station {
    @Json(name = "code")
    private String code;
    @Json(name = "title")
    private String title;
    @Json(name = "station_type")
    private String stationType;
    @Json(name = "popular_title")
    private String popularTitle;
    @Json(name = "short_title")
    private String shortTitle;
    @Json(name = "transport_type")
    private String transportType;
    @Json(name = "station_type_name")
    private String stationTypeName;
    @Json(name = "type")
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Station withCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Station withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public Station withStationType(String stationType) {
        this.stationType = stationType;
        return this;
    }

    public String getPopularTitle() {
        return popularTitle;
    }

    public void setPopularTitle(String popularTitle) {
        this.popularTitle = popularTitle;
    }

    public Station withPopularTitle(String popularTitle) {
        this.popularTitle = popularTitle;
        return this;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public Station withShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
        return this;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public Station withTransportType(String transportType) {
        this.transportType = transportType;
        return this;
    }

    public String getStationTypeName() {
        return stationTypeName;
    }

    public void setStationTypeName(String stationTypeName) {
        this.stationTypeName = stationTypeName;
    }

    public Station withStationTypeName(String stationTypeName) {
        this.stationTypeName = stationTypeName;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Station withType(String type) {
        this.type = type;
        return this;
    }
}

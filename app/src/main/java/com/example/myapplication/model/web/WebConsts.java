package com.example.myapplication.model.web;

public final class WebConsts {
    public static final String PLX_LINK_BASE_URL = "http://176.119.156.25/api/";
    public static final String PLX_LINK_DIRECTIONS_VALID = "directions/valid";
    public static final String PLX_LINK_DIRECTIONS_ALL = "directions/all";
    public static final String PLX_LINK_CHECKSUM = "directions/checksum";

    static final String YANDEX_API_RASP_URL = "https://api.rasp.yandex.net/v3.0/";
    static final String YANDEX_API_PARALLAX_KEY = "ac705998-ef1f-4c68-b387-c2b3243bdca3";

    public static final String YANDEX_API_DIRECTIONS = "search/?apikey=" + WebConsts.YANDEX_API_PARALLAX_KEY + "&result_timezone=RU&format=json&transport_types=train&system=express&transfers=false";
    public static final String YANDEX_API_STATIONS = "thread/?apikey=" + WebConsts.YANDEX_API_PARALLAX_KEY + "&result_timezone=RU&format=json";

    public static final String YANDEX_API_DIRECTIONS_LOCAL = "search/?apikey=" + WebConsts.YANDEX_API_PARALLAX_KEY + "&format=json&transport_types=train&system=express&transfers=false";
    public static final String YANDEX_API_STATIONS_LOCAL = "thread/?result_timezone=RU&apikey=" + WebConsts.YANDEX_API_PARALLAX_KEY + "&format=json";
}

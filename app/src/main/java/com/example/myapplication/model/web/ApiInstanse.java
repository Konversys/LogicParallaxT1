package com.example.myapplication.model.web;

import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiInstanse {
    static ApiPlxLink apiPlxLink;
    static ApiYandex apiYandex;

    private static Retrofit getApiInstanse(String base_url){
        return new Retrofit.Builder().baseUrl(base_url)
                .addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder().build())).build();
    }

    public static ApiPlxLink getPlxLinkApi(){
        if (apiPlxLink == null)
            apiPlxLink = getApiInstanse(WebConsts.PLX_LINK_BASE_URL).create(ApiPlxLink.class);
        return apiPlxLink;
    }

    public static ApiYandex getYandexApi() {
        if (apiYandex == null)
            apiYandex = getApiInstanse(WebConsts.YANDEX_API_RASP_URL).create(ApiYandex.class);
        return apiYandex;
    }
}

package com.example.myapplication.model.web;

import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.plx_link_api.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiPlxLink {
    @GET(WebConsts.PLX_LINK_DIRECTIONS_VALID)
    Call<List<Direction>> getValidDirections();

    @GET(WebConsts.PLX_LINK_DIRECTIONS_ALL)
    Call<List<Direction>> getAllDirections();

    @GET(WebConsts.PLX_LINK_CHECKSUM)
    Call<String> getChecksum();

    @GET(WebConsts.PLX_LINK_PRODUCTS)
    Call<List<Product>> getProducts();
}
